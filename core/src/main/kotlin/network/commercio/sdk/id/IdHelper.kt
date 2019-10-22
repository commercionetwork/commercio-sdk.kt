package network.commercio.sdk.id

import network.commercio.sacco.TxResponse
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sdk.crypto.SignHelper
import network.commercio.sdk.entities.id.*
import network.commercio.sdk.networking.Network
import network.commercio.sdk.tx.TxHelper
import network.commercio.sdk.utils.tryOrNull
import org.spongycastle.util.encoders.Hex

/**
 * Allows to perform common operations related to CommercioID.
 */
object IdHelper {

    suspend fun getDidDocument(did: Did, wallet: Wallet): DidDocument? = tryOrNull {
        Network.query<DidDocument>(url = "${wallet.networkInfo.lcdUrl}/identities/${did.value}")
    }

    /**
     * Performs a transaction setting the specified [didDocument] as being associated with the
     * address present inside the specified [wallet].
     */
    suspend fun setDidDocument(didDocument: DidDocument, wallet: Wallet): TxResponse {
        val msg = MsgSetDidDocument(didDocument)
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet)
    }

    /**
     * Creates a new Did deposit request for the given [recipient] and of the given [amount].
     * Signs everything that needs to be signed (i.e. the signature JSON inside the payload) with the
     * private key contained inside the given [wallet].
     */
    suspend fun requestDidDeposit(recipient: Did, amount: List<StdCoin>, wallet: Wallet): TxResponse {
        // Get the timestamp
        val timestamp = getTimeStamp()

        // Build the signature
        val signatureJson = DidDepositRequestSignatureJson(recipient = recipient.value, timeStamp = timestamp)
        val signedJson = SignHelper.signSorted(signatureJson, wallet)

        // Build the payload
        val payload = DidDepositRequestPayload(
            recipient = recipient.value,
            timeStamp = timestamp,
            signature = Hex.toHexString(signedJson)
        )

        // Build the proof
        val result = generateProof(payload)

        // Build the message and send the tx
        val msg = MsgRequestDidDeposit(
            recipientDid = recipient.value,
            amount = amount,
            depositProof = Hex.toHexString(result.encryptedProof),
            encryptionKey = Hex.toHexString(result.encryptedAesKey),
            senderDid = wallet.bech32Address
        )
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet)
    }

    /**
     * Creates a new Did power up request for the given [pairwiseDid] and of the given [amount].
     * Signs everything that needs to be signed (i.e. the signature JSON inside the payload) with the
     * private key contained inside the given [wallet].
     */
    suspend fun requestDidPowerUp(pairwiseDid: Did, amount: List<StdCoin>, wallet: Wallet): TxResponse {
        // Get the timestamp
        val timestamp = getTimeStamp()

        // Build the signature
        val signatureJson = DidPowerUpRequestSignatureJson(pairwiseDid = pairwiseDid.value, timestamp = timestamp)
        val signedJson = SignHelper.signSorted(signatureJson, wallet)

        // Build the payload
        val payload = DidPowerUpRequestPayload(
            pairwiseDid = pairwiseDid.value,
            timestamp = timestamp,
            signature = Hex.toHexString(signedJson)
        )

        // Build the proof
        val result = generateProof(payload)

        // Build the message and send the tx
        val msg = MsgRequestDidPowerUp(
            claimantDid = pairwiseDid.value,
            amount = amount,
            powerUpProof = Hex.toHexString(result.encryptedProof),
            encryptionKey = Hex.toHexString(result.encryptedAesKey)
        )
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet)
    }
}
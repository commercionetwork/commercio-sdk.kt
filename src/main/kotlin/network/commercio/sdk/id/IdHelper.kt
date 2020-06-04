package network.commercio.sdk.id

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import network.commercio.sacco.TxResponse
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sacco.models.types.StdFee
import network.commercio.sdk.crypto.EncryptionHelper
import network.commercio.sdk.crypto.KeysHelper
import network.commercio.sdk.crypto.SignHelper
import network.commercio.sdk.entities.id.*
import network.commercio.sdk.networking.Network
import network.commercio.sdk.tx.TxHelper
import network.commercio.sdk.tx.TxHelper.BroadcastingMode
import network.commercio.sdk.utils.getTimeStamp
import network.commercio.sdk.utils.readHex
import network.commercio.sdk.utils.toHex
import network.commercio.sdk.utils.tryOrNull
import java.security.interfaces.RSAPrivateKey
import javax.crypto.SecretKey

/**
 * Allows to perform common operations related to CommercioID.
 */
object IdHelper {

    init {
        System.setProperty("javax.net.ssl.trustStoreType", "JKS")
    }

    /**
     * Returns the Did Document associated with the given [did], or `null` if no Did Document was found.
     */
    suspend fun getDidDocument(did: Did, wallet: Wallet): DidDocument? = tryOrNull {
        val result = Network.queryChain<Any>(url = "${wallet.networkInfo.lcdUrl}/identities/${did.value}")
        return@tryOrNull jacksonObjectMapper().convertValue(result, DidDocumentWrapper::class.java).didDoc
    }

    /**
     * Performs a transaction setting the specified [didDocument] as being associated with the
     * address present inside the specified [wallet].
     */
    suspend fun setDidDocument(
        didDocument: DidDocument,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {
        val msg = MsgSetDidDocument(didDocument)
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet, fee = fee, mode = mode)
    }

    /**
     * Performs a transaction setting the specified list of [didDocuments] as being associated with the
     * address present inside the specified [wallet].
     */
    suspend fun setDidDocumentsList(
        didDocuments: List<DidDocument>,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {
        val msgs = didDocuments.map { MsgSetDidDocument(it) }
        return TxHelper.createSignAndSendTx(msgs = msgs, wallet = wallet, fee = fee, mode = mode)
    }

    /**
     * Creates a new Did power up request for the given [pairwiseDid] and of the given [amount].
     * Signs everything that needs to be signed (i.e. the signature JSON inside the payload) with the
     * private key contained inside the given [wallet] and the client generated `signature private RSA key`.
     * Optionally a custom `fee` can be specified.
     */
    suspend fun requestDidPowerUp(
        pairwiseDid: Did,
        amount: List<StdCoin>,
        wallet: Wallet,
        privateKey: RSAPrivateKey,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {

        val requestDidPowerUp= RequestDidPowerUpHelper.fromWallet(
            wallet = wallet,
            pairwiseDid = pairwiseDid,
            amount = amount,
            privateKey = privateKey
        )

        // Build the message and send the tx
        val msg = MsgRequestDidPowerUp(
            claimantDid = requestDidPowerUp.claimantDid,
            amount = requestDidPowerUp.amount,
            powerUpProof = requestDidPowerUp.powerUpProof,
            uuid = requestDidPowerUp.uuid,
            proofKey = requestDidPowerUp.proofKey
        )

        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet, fee = fee, mode = mode)
    }

    /**
     *  Performs a transaction setting the specified list of RequestDidPowerUp in the given [requestDidPowerUps].
     * Optionally a custom `fee` can be specified.
     */
    suspend fun requestDidPowerUpsList(
        requestDidPowerUps: List<RequestDidPowerUp>,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {

        // Build the message and send the tx
        val msgs = requestDidPowerUps.map {
            MsgRequestDidPowerUp(
                claimantDid = it.claimantDid,
                amount = it.amount,
                powerUpProof = it.powerUpProof,
                uuid = it.uuid,
                proofKey = it.proofKey
            )
        }

        return TxHelper.createSignAndSendTx(msgs = msgs, wallet = wallet, fee = fee, mode = mode)
    }

    /**
     * Creates a new [DidConnectionData] instance for the given [recipient] containing the given [pairwiseDid].
     * @param encryptionKey AES key that will be used to encrypt the whole payload.
     */
    suspend fun createConnectionInvitation(
        pairwiseDid: Did,
        recipient: Did,
        encryptionKey: SecretKey,
        wallet: Wallet
    ): DidConnectionData {
        // Get the recipient DidDocument
        val recipientDidDocument = getDidDocument(recipient, wallet)
            ?: throw UnsupportedOperationException("Cannot create a pairwise connection to a user without a Did Document")

        val rsaPublicKey = recipientDidDocument.encryptionKey
            ?: throw UnsupportedOperationException("Recipient has not set an encryption key")

        // Get the timestamp
        val timeStamp = getTimeStamp()

        // Build the signature JSON and sign it
        val signatureJson = DidConnectionData.Payload.SignatureJson(
            timeStamp = timeStamp,
            pairwiseDid = pairwiseDid.value
        )
        val signatureValue = SignHelper.signSorted(signatureJson, wallet)

        // Build the payload and encrypt it
        val payload = DidConnectionData.Payload(
            publicDid = wallet.bech32Address,
            pairwiseDid = pairwiseDid.value,
            timeStamp = timeStamp,
            signature = signatureValue.toHex()
        )
        val payloadString = jacksonObjectMapper().writeValueAsString(payload)
        val encryptedPayload = EncryptionHelper.encryptWithAes(payloadString, encryptionKey)

        // Encrypt the AES key
        val encryptedAes = EncryptionHelper.encryptWithRsa(encryptionKey.encoded, rsaPublicKey)

        // Create the complete data
        return DidConnectionData(
            encryptedAesKey = encryptedAes.toHex(),
            encryptedData = encryptedPayload.toHex()
        )
    }

    /**
     * Verifies that the given [connectionData] is valid and returns the pairwise [Did] data contained inside it.
     * @param decryptionKey private RSA key that is associated to the public RSA encryption key present inside the
     * Did Document associated with the given [wallet]. This key will be used to decrypt the AES key that will be
     * used to get a decrypted payload.
     */
    suspend fun verifyConnectionInvitation(
        connectionData: DidConnectionData,
        decryptionKey: RSAPrivateKey,
        wallet: Wallet
    ): Did {
        // Decrypt the AES key
        val decryptedAesBytes = EncryptionHelper.decryptWithRsa(connectionData.encryptedAesKey.readHex(), decryptionKey)
        val decryptedAes = KeysHelper.recoverAesKey(decryptedAesBytes)

        // Decrypt the payload
        val decryptedPayload = EncryptionHelper.decryptWithAes(connectionData.encryptedData.readHex(), decryptedAes)
        val payloadString = String(decryptedPayload)
        val payload = jacksonObjectMapper().readValue(payloadString, DidConnectionData.Payload::class.java)

        // Get the sender Did Document
        val senderDidDocument = getDidDocument(Did(payload.publicDid), wallet)
            ?: throw UnsupportedOperationException("Connection sender does not have a public Did Document")

        // Build the signature JSON and verify it
        val signatureJson = DidConnectionData.Payload.SignatureJson(
            pairwiseDid = payload.signature,
            timeStamp = payload.timeStamp
        )
        if (!SignHelper.verifySignature(payload.signature.readHex(), signatureJson, senderDidDocument)) {
            throw UnsupportedOperationException("Invalid payload signature")
        }

        // Return the pairwise Did
        return Did(payload.pairwiseDid)
    }
}
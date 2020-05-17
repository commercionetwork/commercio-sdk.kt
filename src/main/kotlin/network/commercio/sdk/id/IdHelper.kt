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
import network.commercio.sdk.utils.getTimeStamp
import network.commercio.sdk.utils.readHex
import org.bouncycastle.util.encoders.Base64
import java.util.Base64 as B64
import network.commercio.sdk.utils.toHex
import network.commercio.sdk.utils.tryOrNull
import java.security.interfaces.RSAPrivateKey
import javax.crypto.SecretKey
import java.util.UUID
import java.util.*

/**
 * Allows to perform common operations related to CommercioID.
 */
object IdHelper {

    /**
     * Returns the Did Document associated with the given [did], or `null` if no Did Document was found.
     */
    suspend fun getDidDocument(did: Did, wallet: Wallet): DidDocument? = tryOrNull {
        Network.queryChain<DidDocument>(url = "${wallet.networkInfo.lcdUrl}/identities/${did.value}")
    }

    /**
     * Performs a transaction setting the specified [didDocument] as being associated with the
     * address present inside the specified [wallet].
     */
    suspend fun setDidDocument(
        didDocument: DidDocument,
        wallet: Wallet,
        fee: StdFee? = null
    ): TxResponse {
        System.setProperty("javax.net.ssl.trustStoreType", "JKS")
        val msg = MsgSetDidDocument(didDocument)
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet, fee = fee)
    }

    /**
     * Creates a new Did power up request for the given [pairwiseDid] and of the given [amount].
     * Signs everything that needs to be signed (i.e. the signature JSON inside the payload) with the
     * private key contained inside the given [wallet].
     */
    suspend fun requestDidPowerUp(
        pairwiseDid: Did,
        amount: List<StdCoin>,
        wallet: Wallet,
        privateKey: RSAPrivateKey,
        fee: StdFee? = null

    ): TxResponse {
        // Get the timestamp
        // TODO: CONTROL IT BECAUSE IT SHOULD BE SETTING TO UTC
        val timestamp = Date().getTime().toString()

        // Build the signature Hash
        val signedSignatureHash =  SignHelper.signPowerUpSignature(
            senderDid =  wallet.bech32Address,
            pairwiseDid =  pairwiseDid.value,
            timestamp =  timestamp,
            privateKey =  privateKey
        )

        // Build the payload
        // TODO: USED java.util.Base64 INSTEAD org.bouncycastle.util.encoders.Base64. SHOULD BE USED bouncycastle
        val payload = DidPowerUpRequestPayload(
            senderDid = wallet.bech32Address,
            pairwiseDid = pairwiseDid.value,
            timestamp = timestamp,
            signature = B64.getEncoder().encodeToString(signedSignatureHash)
        )


        val aesKey = KeysHelper.generateAesKey(128)

        // Build the proof and encrypt with AesGCM
        // AES KEY IS 128 BIT: JAVA LIMITATION
        val encryptedProof = EncryptionHelper.encryptStringWithAesGCM(
            jacksonObjectMapper().writeValueAsString(payload).toByteArray(),
            aesKey
          );

        // =================
        // Encrypt proof key
        // =================

        // Encrypt the key using the Tumbler public RSA key
        val rsaPubTkKey = EncryptionHelper.getGovernmentRsaPubKey(
            wallet.networkInfo.lcdUrl);
        val encryptedProofKey =
            EncryptionHelper.encryptWithRsa(aesKey.getEncoded(), rsaPubTkKey);

        // Build the message and send the tx
        val msg = MsgRequestDidPowerUp(
            claimantDid = wallet.bech32Address,
            amount = amount,
            powerUpProof = B64.getEncoder().encodeToString(encryptedProof),
            uuid =  UUID.randomUUID().toString(),
            proofKey = B64.getEncoder().encodeToString(encryptedProofKey)
        )
        
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet, fee = fee)
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
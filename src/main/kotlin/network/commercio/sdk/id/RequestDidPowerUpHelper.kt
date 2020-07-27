package network.commercio.sdk.id

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sdk.crypto.EncryptionHelper
import network.commercio.sdk.crypto.KeysHelper
import network.commercio.sdk.crypto.SignHelper
import network.commercio.sdk.entities.id.Did
import network.commercio.sdk.entities.id.RequestDidPowerUp
import org.bouncycastle.util.encoders.Base64
import java.nio.charset.Charset
import java.security.interfaces.RSAPrivateKey
import java.util.*

/**
 * Allows to easily build RequestDidPowerUp
 */
object RequestDidPowerUpHelper {

    /**
     * Creates a new Did power up request for the given [pairwiseDid] and of the given [amount].
     * Signs everything that needs to be signed (i.e. the signature JSON inside the payload) with the
     * private key contained inside the given [wallet] and the client generated `signature private RSA key`.
     */
    suspend fun fromWallet(
        wallet: Wallet,
        pairwiseDid: Did,
        amount: List<StdCoin>,
        privateKey: RSAPrivateKey
    ): RequestDidPowerUp {

        // Get the timestamp
        val timestamp = Date().getTime().toString()

        // Build the signature Hash
        val signedSignatureHash = SignHelper.signPowerUpSignature(
            senderDid = wallet.bech32Address,
            pairwiseDid = pairwiseDid.value,
            timestamp = timestamp,
            privateKey = privateKey
        )

        // Build the payload
        val payload = DidPowerUpRequestPayload(
            senderDid = wallet.bech32Address,
            pairwiseDid = pairwiseDid.value,
            timestamp = timestamp,
            signature = Base64.encode(signedSignatureHash).toString(Charset.defaultCharset())
        )

        val aesKey = KeysHelper.generateAesKey(256)

        // Build the proof and encrypt with AesGCM
        // AES KEY IS 128 BIT: JAVA LIMITATION
        val encryptedProof = EncryptionHelper.encryptStringWithAesGCM(
            jacksonObjectMapper().writeValueAsString(payload).toByteArray(),
            aesKey
        )

        // =================
        // Encrypt proof key
        // =================

        // Encrypt the key using the Tumbler public RSA key
        val rsaPubTkKey = EncryptionHelper.getGovernmentRsaPubKey(
            wallet.networkInfo.lcdUrl
        )
        val encryptedProofKey =
            EncryptionHelper.encryptWithRsa(aesKey.getEncoded(), rsaPubTkKey)

        return RequestDidPowerUp(
            claimantDid = wallet.bech32Address,
            amount = amount,
            powerUpProof = Base64.encode(encryptedProof).toString(Charset.defaultCharset()),
            uuid = UUID.randomUUID().toString(),
            proofKey = Base64.encode(encryptedProofKey).toString(Charset.defaultCharset())
        )
    }
}

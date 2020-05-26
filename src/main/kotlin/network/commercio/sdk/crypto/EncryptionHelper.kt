package network.commercio.sdk.crypto

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import network.commercio.sdk.entities.id.DidDocumentWrapper
import network.commercio.sdk.networking.Network
import org.bouncycastle.util.encoders.Base64
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.cert.X509Certificate
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Allows to perform common encryption operations such as RSA/AES encryption and decryption.
 */
object EncryptionHelper {

    private const val RSA_ALGORITHM = "RSA/ECB/PKCS1Padding"
    private const val AES_ALGORITHM = "AES"
    private const val AES_ALGORITHM_GCM = "AES"

    /**
     * Returns the RSA public key associated to the government that should be used when
     * encrypting the data that only it should see.
     */
    suspend fun getGovernmentRsaPubKey(lcdUrl: String): PublicKey {

        val tumblerResponse = Network.get<Map<String, Any>>("$lcdUrl/government/tumbler")
            ?: throw UnsupportedOperationException("Cannot get tumbler address")

        val tumblerAddress = (tumblerResponse["result"] as Map<String, String>)["tumbler_address"]
            ?: throw UnsupportedOperationException("Missing tumbler_address in response")

        val responsePublicKeyPem = Network.queryChain<Any>("$lcdUrl/identities/$tumblerAddress")
            ?: throw UnsupportedOperationException("Cannot get government RSA public key")

        // get first RsaSignatureKey2018
        val publicKeyPem = (jacksonObjectMapper().convertValue(responsePublicKeyPem, DidDocumentWrapper::class.java).didDoc.publicKeys)
            .filter { it.type=="RsaSignatureKey2018" }.first().publicKeyPem

        val cleaned = publicKeyPem
            .replace("\n", "")
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")

        val keySpec = X509EncodedKeySpec(Base64.decode(cleaned))
        return KeyFactory.getInstance("RSA").generatePublic(keySpec)
    }

    /**
     * Encrypts the given [data] with AES using the specified [key].
     */
    fun encryptWithAes(data: String, key: SecretKey): ByteArray {
        return encryptWithAes(data.toByteArray(Charsets.UTF_8), key)
    }

    /**
     * Encrypts the given [data] with AES using the specified [key].
     */
    fun encryptWithAes(data: ByteArray, key: SecretKey): ByteArray {
        return Cipher.getInstance(AES_ALGORITHM).apply {
            init(Cipher.ENCRYPT_MODE, key)
        }.doFinal(data)
    }

    /**
     * Decrypts the given [data] with AES using the specified [key].
     */
    fun decryptWithAes(data: ByteArray, key: SecretKey): ByteArray {
        return Cipher.getInstance(AES_ALGORITHM).apply {
            init(Cipher.DECRYPT_MODE, key)
        }.doFinal(data)
    }

   /**
     * Encrypts the given [data] with AES-GCM using the specified [key].
     */
    fun encryptStringWithAesGCM(data: ByteArray, key: SecretKey): ByteArray {
        val nonce = KeysHelper.generateNonce()
        val gcmSpec = GCMParameterSpec(128, nonce) // 128 bit authentication tag
        val ciphertext = Cipher.getInstance("AES/GCM/NoPadding").apply {
            init(Cipher.ENCRYPT_MODE, key, gcmSpec)
        }.doFinal(data)
        return nonce + ciphertext
    }

    /**
     * Encrypts the given [data] with RSA and the specified [key].
     */
    fun encryptWithRsa(data: String, key: PublicKey): ByteArray {
        return encryptWithRsa(data.toByteArray(Charsets.UTF_8), key)
    }

    /**
     * Encrypts the given [data] with RSA using the specified [key].
     */
    fun encryptWithRsa(data: ByteArray, key: PublicKey): ByteArray {
        return Cipher.getInstance(RSA_ALGORITHM).apply {
            init(Cipher.ENCRYPT_MODE, key)
        }.doFinal(data)
    }

    /**
     * Encrypts the given [data] with RSA using the specified [certificate].
     */
    fun encryptWithRsa(data: String, certificate: X509Certificate): ByteArray {
        return encryptWithRsa(data.toByteArray(Charsets.UTF_8), certificate)
    }

    /**
     * Encrypts the given [data] with RSA using the specified [certificate].
     */
    fun encryptWithRsa(data: ByteArray, certificate: X509Certificate): ByteArray {
        return Cipher.getInstance(RSA_ALGORITHM).apply {
            init(Cipher.ENCRYPT_MODE, certificate)
        }.doFinal(data)
    }

    /**
     * Decrypts the given data using the specified private [key].
     */
    fun decryptWithRsa(data: ByteArray, key: PrivateKey): ByteArray {
        return Cipher.getInstance(RSA_ALGORITHM).apply {
            init(Cipher.DECRYPT_MODE, key)
        }.doFinal(data)
    }
}
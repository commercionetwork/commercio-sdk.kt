package network.commercio.sdk.crypto

import network.commercio.sdk.networking.Network
import org.bouncycastle.util.encoders.Base64
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.cert.X509Certificate
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.SecretKey

/**
 * Allows to perform common encryption operations such as RSA/AES encryption and decryption.
 */
object EncryptionHelper {

    /**
     * Returns the RSA public key associated to the government that should be used when
     * encrypting the data that only it should see.
     */
    suspend fun getGovernmentRsaPubKey(): PublicKey {
        val response = Network.get<String>("http://localhost:8080/govPublicKey")
            ?: throw UnsupportedOperationException("Cannot get government RSA public key")

        val cleaned = response
            .replace("\n", "")
            .replace("-----BEGIN RSA PUBLIC KEY-----", "")
            .replace("-----END RSA PUBLIC KEY-----", "")

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
        return Cipher.getInstance("AES").apply {
            init(Cipher.ENCRYPT_MODE, key)
        }.doFinal(data)
    }

    /**
     * Decrypts the given [data] with AES using the specified [key].
     */
    fun decryptWithAes(data: ByteArray, key: SecretKey): ByteArray {
        return Cipher.getInstance("AES").apply {
            init(Cipher.DECRYPT_MODE, key)
        }.doFinal(data)
    }

    /**
     * Encrypts the given [data] with RSA and the specified key.
     */
    fun encryptWithRsa(data: String, key: PublicKey): ByteArray {
        return encryptWithRsa(data.toByteArray(Charsets.UTF_8), key)
    }

    /**
     * Encrypts the given [data] with RSA using the specified [key].
     */
    fun encryptWithRsa(data: ByteArray, key: PublicKey): ByteArray {
        return Cipher.getInstance("RSA").apply {
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
        return Cipher.getInstance("RSA").apply {
            init(Cipher.ENCRYPT_MODE, certificate)
        }.doFinal(data)
    }

    /**
     * Decrypts the given data using the specified private [key].
     */
    fun decryptWithRsa(data: ByteArray, key: PrivateKey): ByteArray {
        return Cipher.getInstance("RSA").apply {
            init(Cipher.DECRYPT_MODE, key)
        }.doFinal(data)
    }
}
package network.commercio.sdk.crypto

import java.security.Key
import java.security.PublicKey
import java.security.cert.X509Certificate
import javax.crypto.Cipher

/**
 * Allows to perform common encryption operations such as RSA/AES encryption and decryption.
 */
object EncryptionHelper {

    /**
     * Encrypts the given [data] with AES using the specified [key].
     */
    fun encryptWithAes(data: String, key: Key): ByteArray {
        return encryptWithAes(data.toByteArray(Charsets.UTF_8), key)
    }

    /**
     * Encrypts the given [data] with AES using and the specified [key]
     */
    fun encryptWithAes(data: ByteArray, key: Key): ByteArray {
        return Cipher.getInstance("AES").apply {
            init(Cipher.ENCRYPT_MODE, key)
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
        return Cipher.getInstance("RSAWithSHA256").apply {
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
        return Cipher.getInstance("RSAWithSHA256").apply {
            init(Cipher.ENCRYPT_MODE, certificate)
        }.doFinal(data)
    }
}
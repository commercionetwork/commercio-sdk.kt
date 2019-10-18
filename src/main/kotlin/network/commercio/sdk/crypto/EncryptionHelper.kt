package network.commercio.sdk.crypto

import java.security.Key
import javax.crypto.Cipher
import javax.crypto.SecretKey

object EncryptionHelper {

    fun encryptWithAes(data: String, key: SecretKey): ByteArray {
        return encryptWithAes(data.toByteArray(Charsets.UTF_8), key)
    }

    fun encryptWithAes(data: ByteArray, key: SecretKey): ByteArray {
        return Cipher.getInstance("AES").apply {
            init(Cipher.ENCRYPT_MODE, key)
        }.doFinal(data)
    }

    fun encryptWithRsa(data: String, key: Key): ByteArray {
        return encryptWithRsa(data.toByteArray(Charsets.UTF_8), key)
    }

    fun encryptWithRsa(data: ByteArray, key: Key): ByteArray {
        return Cipher.getInstance("RSAWithSHA256").apply {
            init(Cipher.ENCRYPT_MODE, key)
        }.doFinal(data)
    }

}
package network.commercio.sdk.crypto

import java.security.KeyPair
import java.security.KeyPairGenerator
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object KeysHelper {

    fun generateAesKey(): SecretKey {
        return KeyGenerator.getInstance("AES").apply {
            init(256)
        }.generateKey()
    }

    fun generateRsaKeyPair(): KeyPair {
        return KeyPairGenerator.getInstance("RSA").apply {
            initialize(512)
        }.generateKeyPair()
    }
}
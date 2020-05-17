package network.commercio.sdk.cyrpto

import network.commercio.sdk.crypto.EncryptionHelper
import network.commercio.sdk.readResource
import org.bouncycastle.util.encoders.Base64
import org.bouncycastle.util.encoders.Hex
import org.junit.Assert.assertEquals
import org.junit.Test
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.spec.SecretKeySpec
import java.security.interfaces.RSAPrivateKey
import javax.crypto.SecretKey

/**
 * Tests for [EncryptionHelper].
 */
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class EncryptionHelperTests {

    private val aesKey = SecretKeySpec("Xn2r5u8x/A?D(G+KbPdSgVkYp3s6v9y$".toByteArray(), "AES")

    private val rsaPubKey: PublicKey
        get() {
            val stripped = readResource("rsa_public_key.pem")
            return KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(Base64.decode(stripped))) as PublicKey
        }

    private val rsaPrivateKey: PrivateKey
        get() {
            val stripped = readResource("rsa_private_key.pem")
            return KeyFactory.getInstance("RSA").generatePrivate(PKCS8EncodedKeySpec(Base64.decode(stripped))) as PrivateKey
        }


    @Test
    fun `encryptWithAes works with String data`() {
        val result = EncryptionHelper.encryptWithAes("Test", aesKey)
        assertEquals("567b77516c6d96978561ee8244b01afb", Hex.toHexString(result))
    }

    @Test
    fun `encryptWithAes works properly with ByteArray data`() {
        val input = "Super long test that should be encrypted properly"
        val result = EncryptionHelper.encryptWithAes(input.toByteArray(), aesKey)
        assertEquals(
            "8031b6fb67ee4cf45e5bff5e6927f016675ed9c8e89b5aed8f9418c8ca04b65706c71a65039302e937342eed892be761251bb3596b64145060fd478a2fe839c7",
            Hex.toHexString(result)
        )
    }

    @Test
    fun `encryptWithRsa works with String data`() {
        val input = "This is a test!"
        val encrypted = EncryptionHelper.encryptWithRsa(input, rsaPubKey)
        val decrypted = EncryptionHelper.decryptWithRsa(encrypted, rsaPrivateKey)
        assertEquals(input, String(decrypted))
    }

    @Test
    fun `encryptWithRsa works with ByteArray data`() {
        val input = "Long text for RSA encryption and decryption that should be read as a ByteArray"
        val encrypted = EncryptionHelper.encryptWithRsa(input.toByteArray(), rsaPubKey)
        val decrypted = EncryptionHelper.decryptWithRsa(encrypted, rsaPrivateKey)
        assertEquals(input, String(decrypted))
    }
}
package network.commercio.sdk.crypto

import java.security.KeyFactory
import java.security.interfaces.RSAPrivateKey
import java.security.spec.PKCS8EncodedKeySpec

/**
 * Allows to easily parse a PEM-encoded RSA public key.
 */
object RSAKeyParser {

    /**
     *  Get RSAPrivateKey from the given [privateKeyPem] as a PEM-encoded RSA key.
     */
    fun parseRSAPrivateKeyFromPem(privateKeyPem: String): RSAPrivateKey {

        val privateCleaned = privateKeyPem
            .replace("\n", "")
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")

        val encoded: ByteArray = java.util.Base64.getDecoder().decode(privateCleaned)
        val kf: KeyFactory = KeyFactory.getInstance("RSA")
        val keySpec = PKCS8EncodedKeySpec(encoded)
        return kf.generatePrivate(keySpec) as RSAPrivateKey
    }
}

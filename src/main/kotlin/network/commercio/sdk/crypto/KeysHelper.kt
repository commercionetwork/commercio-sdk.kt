package network.commercio.sdk.crypto

import KeyPairWrapper
import PublicKeyWrapper
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Hex
import java.security.*
import java.security.spec.ECGenParameterSpec
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import java.security.SecureRandom


/**
 * Allows to easily generate new keys either to be used with AES or RSA key.
 */
object KeysHelper {
    /**
     * Be sure to use a SecureRandom!
     */
    val secureRandom = SecureRandom()

    fun generateNonce(): ByteArray {
        val result = ByteArray(96 / 8)
        secureRandom.nextBytes(result)
        return result
    }

    fun recoverAesKey(bytes: ByteArray): SecretKey {
        return SecretKeySpec(bytes, "AES")
    }

    /**
     * Generates a new random AES secret key with initializing vector.
     */
    fun generateAesKey(bytes: Int = 256): SecretKey {
        return KeyGenerator.getInstance("AES").apply {
            init(bytes)
        }.generateKey()
    }

    /**
     * Generates a new RSA key pair having the given [bytes] length.
     * If no length is specified, the default is going to be 2048.
     */
    fun generateRsaKeyPair(bytes: Int = 2048, type: String="RsaVerificationKey2018"): KeyPairWrapper {
        val keyPair: KeyPair = KeyPairGenerator.getInstance("RSA").apply {
            initialize(bytes)
        }.generateKeyPair()
        return KeyPairWrapper(PublicKeyWrapper(public = keyPair.public, type = type),keyPair.private)
    }


    fun generateEcKeyPair(type: String="EcdsaSecp256k1VerificationKey2019"): KeyPairWrapper {
        val keyPair= KeyPairGenerator.getInstance("EC", BouncyCastleProvider()).apply {
            initialize(ECGenParameterSpec("secp256k1"), SecureRandom())
        }.generateKeyPair()
        return KeyPairWrapper(PublicKeyWrapper(public = keyPair.public, type = type),keyPair.private)
    }

    /**
     * Export public [key] of [type] RSA or EC into an HEX string.
     */
    fun exportPublicKeyHEX(key: PublicKey, type: String): String {
        val fact = KeyFactory.getInstance(type)
        val spec = fact.getKeySpec(key,
            X509EncodedKeySpec::class.java)
        return Hex.toHexString(spec.encoded)
    }

    /**
     * Export private [key] of [type] RSA or EC into an HEX string.
     */
    fun exportPrivateKeyHEX(key: PrivateKey, type: String): String {
        val fact = KeyFactory.getInstance(type)
        val spec = fact.getKeySpec(key,
            PKCS8EncodedKeySpec::class.java)
        return Hex.toHexString(spec.encoded)
    }

    /**
     * Sign [data] with the given [privateKey] and [digestAlgorithm]
     * algorithm can be either SHA256withRSA, SHA1withECDSA.
     */
    fun signData(data: String, privateKey: PrivateKey, digestAlgorithm: String): String {
        val privateSignature = Signature.getInstance(digestAlgorithm)
        privateSignature.initSign(privateKey)
        privateSignature.update(Hex.decode(data))

        val signature = privateSignature.sign()

        return Hex.toHexString(signature)
    }

    /**
     * Verify the [signedData] with the given [publicKey] and [digestAlgorithm].
     * algorithm can be either SHA256withRSA, SHA1withECDSA.
     */
    fun verifySignedData(data: String, signedData: String, publicKey: PublicKey, digestAlgorithm: String): Boolean {

        val publicSignature = Signature.getInstance(digestAlgorithm)
        publicSignature.initVerify(publicKey)
        publicSignature.update(Hex.decode(data))

        val signatureBytes = Hex.decode(signedData)

        return publicSignature.verify(signatureBytes)
    }
}

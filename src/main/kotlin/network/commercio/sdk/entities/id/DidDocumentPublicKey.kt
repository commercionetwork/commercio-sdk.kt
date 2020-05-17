package network.commercio.sdk.entities.id


import com.fasterxml.jackson.annotation.JsonProperty
import network.commercio.sdk.utils.readHex
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec

/**
 * Contains the data of public key contained inside a Did document.
 */
data class DidDocumentPublicKey(
    @field:JsonProperty("id") val id: String,
    @field:JsonProperty("type") val type: String,
    @field:JsonProperty("controller") val controller: String,
    @field:JsonProperty("publicKeyPem") val publicKeyPem: String
) {
    /**
     * Converts this key into a [PublicKey] instance.
     * @return the [PublicKey] instance, or `null` if this key is not supported.
     */
    fun toPublicKey(): PublicKey? {
        val bytes = publicKeyPem.readHex()
        val keyFactory = when (type) {
            "RsaVerificationKey2018", "RsaSignatureKey2018" -> KeyFactory.getInstance("RSA")
            "Secp256k1VerificationKey2018" -> KeyFactory.getInstance("EC")
            "Ed25519VerificationKey2018" -> {
                println("Ed25519 keys not supported yet")
                return null
            }
            else -> null
        }

        return keyFactory?.generatePublic(PKCS8EncodedKeySpec(bytes))
    }

    enum class Type {
        @JsonProperty("RsaVerificationKey2018") RSA,
        @JsonProperty("Ed25519VerificationKey2018") ED25519,
        @JsonProperty("Secp256k1VerificationKey2018") SECP256K1
    }
}


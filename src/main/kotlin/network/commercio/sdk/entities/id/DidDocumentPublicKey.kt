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
    @field:JsonProperty("type") val type: Type,
    @field:JsonProperty("controller") val controller: String,
    @field:JsonProperty("publicKeyHex") val publicKeyHex: String
) {

    enum class Type {
        @JsonProperty("RsaVerificationKey2018") RSA,
        @JsonProperty("Ed25519VerificationKey2018") ED25519,
        @JsonProperty("Secp256k1VerificationKey2018") SECP256K1
    }

    /**
     * Converts this key into a [PublicKey] instance.
     * @return the [PublicKey] instance, or `null` if this key is not supported.
     */
    fun toPublicKey(): PublicKey? {
        val bytes = publicKeyHex.readHex()
        val keyFactory = when (type) {
            Type.RSA -> KeyFactory.getInstance("RSA")
            Type.SECP256K1 -> KeyFactory.getInstance("EC")
            Type.ED25519 -> {
                println("Ed25519 keys not supported yet")
                return null
            }
        }

        return keyFactory.generatePublic(PKCS8EncodedKeySpec(bytes))
    }
}

package network.commercio.sdk.entities.id

import com.fasterxml.jackson.annotation.JsonProperty

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
}

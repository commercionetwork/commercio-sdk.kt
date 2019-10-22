package network.commercio.sdk.id

import com.fasterxml.jackson.annotation.JsonProperty
import network.commercio.sdk.entities.id.DidDocument
import network.commercio.sdk.entities.id.DidDocumentPublicKey

/**
 * Represents the contents of the JSON object that should be signed and used inside a [DidDocument]
 * object as the value of the `proof.signatureValue` field.
 */
data class DidDocumentProofSignatureContent(
    @field:JsonProperty("@context") val context: String,
    @field:JsonProperty("id") val did: String,
    @field:JsonProperty("publicKey") val publicKeys: List<DidDocumentPublicKey>,
    @field:JsonProperty("authentication") val authentication: List<String>
)
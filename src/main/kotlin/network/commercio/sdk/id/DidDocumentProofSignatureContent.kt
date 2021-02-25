package network.commercio.sdk.id

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import network.commercio.sdk.entities.id.DidDocument
import network.commercio.sdk.entities.id.DidDocumentPublicKey
import network.commercio.sdk.entities.id.DidDocumentService

/**
 * Represents the contents of the JSON object that should be signed and used inside a [DidDocument]
 * object as the value of the `proof.signatureValue` field.
 */
data class DidDocumentProofSignatureContent(
    @JsonProperty("@context") val context: String,
    @JsonProperty("id") val id: String,
    @JsonProperty("publicKey") val publicKeys: List<DidDocumentPublicKey>,
    @JsonProperty("service") @JsonInclude(JsonInclude.Include.NON_EMPTY) val service: List<DidDocumentService>? = null
)
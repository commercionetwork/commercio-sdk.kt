package network.commercio.sdk.entities.id

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Contains the data of the proof that makes sure the user has properly signed the Did Document contents
 * with his authentication key so that he can guarantee that he is the real controller of the Did Document itself.
 */
data class DidDocumentProof(
    @JsonProperty("type") val type: String,
    @JsonProperty("created") val creationTimeStamp: String,
    @JsonProperty("creator") val creatorKeyId: String,
    @JsonProperty("signatureValue") val signatureValue: String
)
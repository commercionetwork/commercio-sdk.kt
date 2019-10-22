package network.commercio.sdk.id

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents the JSON object that should be signed and put
 * inside a [DidPowerUpRequestPayload] as the signature value.
 */
data class DidPowerUpRequestSignatureJson(
    @JsonProperty("pairwise_did") val pairwiseDid: String,
    @JsonProperty("timestamp") val timestamp: String
)
package network.commercio.sdk.id

import com.fasterxml.jackson.annotation.JsonProperty

data class DidPowerUpRequestSignatureJson(
    @JsonProperty("pairwise_did") val pairwiseDid: String,
    @JsonProperty("timestamp") val timestamp: String
)

data class DidPowerUpRequestPayload(
    @JsonProperty("pairwise_did") val pairwiseDid: String,
    @JsonProperty("timestamp") val timestamp: String,
    @JsonProperty("signature") val signature: String
)
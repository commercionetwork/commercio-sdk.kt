package network.commercio.sdk.id

import com.fasterxml.jackson.annotation.JsonProperty

data class DidDepositRequestSignatureJson (
    @JsonProperty("recipient") val recipient: String,
    @JsonProperty("timestamp") val timeStamp: String
)

data class DidDepositRequestPayload(
    @JsonProperty("recipient") val recipient: String,
    @JsonProperty("timestamp") val timeStamp: String,
    @JsonProperty("signature") val signature: String
)
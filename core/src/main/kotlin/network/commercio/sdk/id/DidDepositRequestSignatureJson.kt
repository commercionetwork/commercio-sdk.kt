package network.commercio.sdk.id

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents the JSON object that should be created, signed and put
 * inside a [DidDepositRequestPayload] as the signature value.
 */
data class DidDepositRequestSignatureJson (
    @JsonProperty("recipient") val recipient: String,
    @JsonProperty("timestamp") val timeStamp: String
)

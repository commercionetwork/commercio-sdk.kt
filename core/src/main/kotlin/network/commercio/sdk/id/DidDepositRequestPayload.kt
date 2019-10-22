package network.commercio.sdk.id

import com.fasterxml.jackson.annotation.JsonProperty
import network.commercio.sdk.entities.id.MsgRequestDidDeposit

/**
 * Represents the JSON object that should be encrypted and put
 * inside a [MsgRequestDidDeposit] as its payload.
 */
data class DidDepositRequestPayload(
    @JsonProperty("recipient") val recipient: String,
    @JsonProperty("timestamp") val timeStamp: String,
    @JsonProperty("signature") val signature: String
)
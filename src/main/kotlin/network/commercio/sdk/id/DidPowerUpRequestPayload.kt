package network.commercio.sdk.id

import com.fasterxml.jackson.annotation.JsonProperty
import network.commercio.sdk.entities.id.MsgRequestDidPowerUp

/**
 * Represents the payload that should be put inside a
 * [MsgRequestDidPowerUp] message.
 */
data class DidPowerUpRequestPayload(
    @JsonProperty("sender_did") val senderDid: String,
    @JsonProperty("pairwise_did") val pairwiseDid: String,
    @JsonProperty("timestamp") val timestamp: String,
    @JsonProperty("signature") val signature: String
)
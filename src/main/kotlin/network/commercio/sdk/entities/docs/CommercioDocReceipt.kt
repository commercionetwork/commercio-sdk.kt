package network.commercio.sdk.entities.docs

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import network.commercio.sdk.utils.matchBech32Format
import network.commercio.sdk.utils.matchUuidv4

/**
 * Represents a document receipt that indicates that the document having the given [documentUuid]
 * present inside the transaction with has [txHash] and sent by [recipientDid] has been received from the [senderDid].
 * @property proof optional reading proof.
 */
data class CommercioDocReceipt(
    @JsonProperty("uuid") val uuid: String,
    @JsonProperty("sender") val senderDid: String,
    @JsonProperty("recipient") val recipientDid: String,
    @JsonProperty("tx_hash") val txHash: String,
    @JsonProperty("document_uuid") val documentUuid: String,
    @JsonProperty("proof") @JsonInclude(JsonInclude.Include.NON_EMPTY) val proof: String = ""
) {
    init {
        require(matchUuidv4(uuid)) { "uuid requires a valid UUID v4 format" }
        require(matchBech32Format(senderDid)) { "sender requires a valid Bech32 format" }
        require(matchBech32Format(recipientDid)) { "recipient requires a valid Bech32 format" }
        require(matchUuidv4(documentUuid)) { "document_uuid requires a valid UUID v4 format" }
    }
}
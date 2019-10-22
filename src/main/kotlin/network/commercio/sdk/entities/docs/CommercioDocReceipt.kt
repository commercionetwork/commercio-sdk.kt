package network.commercio.sdk.entities.docs

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents a document receipt that indicates that the document having the given [documentUuid]
 * present inside the transaction with has [txHash] and sent by [recipientDid] has been received from the [senderDid].
 * @property proof optional reading proof.
 */
data class CommercioDocReceipt(
    @JsonProperty("sender") val senderDid: String,
    @JsonProperty("recipient") val recipientDid: String,
    @JsonProperty("tx_hash") val txHash: String,
    @JsonProperty("document_uuid") val documentUuid: String,
    @JsonProperty("proof") val proof: String?
)
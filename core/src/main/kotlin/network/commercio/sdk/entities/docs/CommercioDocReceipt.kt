package network.commercio.sdk.entities.docs

/**
 * Represents a document receipt that indicates that the document having the given [documentUuid]
 * present inside the transaction with has [txHash] and sent by [recipientDid] has been received from the [senderDid].
 * @property proof optional reading proof.
 */
data class CommercioDocReceipt(
    val senderDid: String,
    val recipientDid: String,
    val txHash: String,
    val documentUuid: String,
    val proof: String?
)
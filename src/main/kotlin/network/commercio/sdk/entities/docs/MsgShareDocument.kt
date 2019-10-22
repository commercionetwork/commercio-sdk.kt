package network.commercio.sdk.entities.docs

import network.commercio.sacco.models.types.StdMsg

/**
 * Represents the transaction message that must be used when wanting to share a document from one user to another one.
 */
data class MsgShareDocument(
    private val senderDid: String,
    private val recipientsDid: List<String>,
    private val document: CommercioDoc
) : StdMsg(
    type = "commercio/MsgShareDocument",
    value = mapOf(
        "sender" to senderDid,
        "recipients" to recipientsDid,
        "document" to document
    )
)
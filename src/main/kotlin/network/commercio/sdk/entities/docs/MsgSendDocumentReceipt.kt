package network.commercio.sdk.entities.docs

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import network.commercio.sacco.models.types.StdMsg

/**
 * Message that should be used when wanting to send a document receipt transaction.
 */
data class MsgSendDocumentReceipt(
    private val receipt: CommercioDocReceipt
) : StdMsg(
    type = "commercio/MsgSendDocumentReceipt",
    value = jacksonObjectMapper().convertValue(receipt, object : TypeReference<Map<String, Any?>>() {})
)
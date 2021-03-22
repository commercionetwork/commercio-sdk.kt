package network.commercio.sdk.entities.docs.legacy.`21`

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import network.commercio.sacco.models.types.StdMsg
import network.commercio.sdk.entities.docs.legacy.`21`.CommercioDocReceipt as CommercioDocReceiptLegacy

/**
 * Message that should be used when wanting to send a document receipt transaction.
 */
data class MsgSendDocumentReceipt(
    private val receipt: CommercioDocReceiptLegacy
) : StdMsg(
    type = "commercio/MsgSendDocumentReceipt",
    value = jacksonObjectMapper().convertValue(receipt, object : TypeReference<Map<String, Any?>>() {})
)
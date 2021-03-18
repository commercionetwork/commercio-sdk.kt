package network.commercio.sdk.entities.docs.legacy.`21`

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import network.commercio.sacco.models.types.StdMsg
import network.commercio.sdk.entities.docs.legacy.`21`.CommercioDoc as CommercioDocLegacy

/**
 * Represents the transaction message that must be used when wanting to share a document from one user to another one.
 */
data class MsgShareDocument(private val document: CommercioDocLegacy) : StdMsg(
    type = "commercio/MsgShareDocument",
    value = jacksonObjectMapper().convertValue(document, object : TypeReference<Map<String, Any?>>() {})
)
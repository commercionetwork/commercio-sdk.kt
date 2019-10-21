package network.commercio.sdk.entities.id

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import network.commercio.sacco.models.types.StdMsg
import network.commercio.sdk.entities.id.DidDocument

data class MsgSetDidDocument(private val didDocument: DidDocument) : StdMsg(
    type = "commercio/MsgSetIdentity",
    value = jacksonObjectMapper().convertValue(didDocument, object : TypeReference<Map<String, Any>>() {})
)
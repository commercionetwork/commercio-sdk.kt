package network.commercio.sdk.entities.docs

import network.commercio.sdk.entities.msgObjectMapper
import network.commercio.sdk.readResource
import org.junit.Assert.assertEquals
import org.junit.Test

class MsgSendDocumentReceiptTest {

    @Test
    fun `toJson works properly`() {
        val receipt = CommercioDocReceipt(
            uuid = "58961404-a83c-4117-bb2b-6d22a0e19acb",
            senderDid = "did:com:1ttwtq7kxustrqxstjpcjf7wf7l9ljd8jz7z64r",
            recipientDid = "did:com:1xlp0adcdl8363q9y833084cwuzhqy7fucmqh9r",
            txHash = "2CB2471712192815191A679268E4993C67CAD1654FED8F398B698F57EF4A23C1",
            documentUuid = "688a1ef0-04da-4524-b7ca-6e5e3b7e61dc",
            proof = ""
        )
        val msg = MsgSendDocumentReceipt(receipt)

        val expected = readResource("msgs/MsgSendDocumentReceipt.json")
        assertEquals(expected, msgObjectMapper.writeValueAsString(msg))
    }
}
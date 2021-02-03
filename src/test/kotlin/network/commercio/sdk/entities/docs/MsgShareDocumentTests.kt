package network.commercio.sdk.entities.docs

import network.commercio.sdk.entities.msgObjectMapper
import network.commercio.sdk.readResource
import org.junit.Assert.assertEquals
import org.junit.Test

class MsgShareDocumentTests {

    @Test
    fun `toJson works properly`() {
        val msg = MsgShareDocument(
            document = CommercioDoc(
                senderDid = "did:com:1xlp0adcdl8363q9y833084cwuzhqy7fucmqh9r",
                recipientsDids = listOf("did:com:1ttwtq7kxustrqxstjpcjf7wf7l9ljd8jz7z64r"),
                checksum = CommercioDoc.Checksum(
                    algorithm = CommercioDoc.Checksum.Algorithm.MD5,
                    value = "7815696ecbf1c96e6894b779456d330e"
                ),
                contentUri = "https://www.vargroup.it/managed-security-services/",
                encryptionData = CommercioDoc.EncryptionData(
                    encryptedData = listOf(EncryptedData.CONTENT),
                    keys = listOf(
                        CommercioDoc.EncryptionData.Key(
                            recipientDid = "did:com:1ttwtq7kxustrqxstjpcjf7wf7l9ljd8jz7z64r",
                            value = "key1"
                        )
                    )
                ),
                metadata = CommercioDoc.Metadata(
                    contentUri = "https://www.vargroup.it/managed-security-services/",
                    schema = CommercioDoc.Metadata.Schema(
                        version = "1.1.0",
                        uri = "https://www.vargroup.it/managed-security-services/metadata/schema"
                    )
                ),
                uuid = "688a1ef0-04da-4524-b7ca-6e5e3b7e61dc"
            )
        )

        val expected = readResource("msgs/MsgShareDocument.json")
        assertEquals(expected, msgObjectMapper.writeValueAsString(msg))
    }
}
package network.commercio.sdk.entities.id

import network.commercio.sacco.models.types.StdCoin
import network.commercio.sdk.entities.msgObjectMapper
import network.commercio.sdk.readResource
import org.junit.Assert.assertEquals
import org.junit.Test

class MsgRequestDidDepositTests {

    @Test
    fun `toJson works properly`() {
        val msg = MsgRequestDidDeposit(
            amount = listOf(
                StdCoin(amount = "100", denom = "ucommercio")
            ),
            encryptionKey = "AES-256 key",
            senderDid = "did:com:1xlp0adcdl8363q9y833084cwuzhqy7fucmqh9r",
            depositProof = "Proof",
            recipientDid = "did:com:1ttwtq7kxustrqxstjpcjf7wf7l9ljd8jz7z64r"
        )

        val expected = readResource("msgs/MsgRequestDidDeposit.json")
        assertEquals(expected, msgObjectMapper.writeValueAsString(msg))
    }
}
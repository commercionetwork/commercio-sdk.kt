package network.commercio.sdk.entities.mint

import kotlinx.coroutines.runBlocking
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sdk.entities.msgObjectMapper
import network.commercio.sdk.mint.MintCccHelper
import org.junit.Assert
import org.junit.Test
import java.util.*

class MsgMintCccTest {

    @Test
    fun `toJson works properly`() = runBlocking {
        val mnemonic = listOf(
            "dash",
            "ordinary",
            "anxiety",
            "zone",
            "slot",
            "rail",
            "flavor",
            "tortoise",
            "guilt",
            "divert",
            "pet",
            "sound",
            "ostrich",
            "increase",
            "resist",
            "short",
            "ship",
            "lift",
            "town",
            "ice",
            "split",
            "payment",
            "round",
            "apology"
        )
        val chain = NetworkInfo(bech32Hrp = "did:com:", lcdUrl = "")
        val wallet = Wallet.derive(mnemonic = mnemonic, networkInfo = chain)

        val depositAmount = listOf(StdCoin(denom = "uccc", amount = "10"))
        val id = UUID.randomUUID().toString()

        val mintCcc1 = MintCcc(
            depositorDid = wallet.bech32Address,
            depositAmount = depositAmount,
            id = id
        )

        val msg1 = MsgMintCcc(mintCcc1)
        Assert.assertNotNull(msg1)

        val mintCcc2 = MintCccHelper.fromWallet(
            wallet = wallet,
            amount = depositAmount,
            id = id
        )

        val msg2 = MsgMintCcc(mintCcc2)
        Assert.assertNotNull(msg2)
        Assert.assertEquals(msg1.toString(), msg2.toString())

        print(msgObjectMapper.writeValueAsString(msg1))

        val jsonResult =
            """{"type":"commercio/MsgMintCCC","value":{"deposit_amount":[{"denom":"uccc","amount":"10"}],"depositor":"did:com:1gkfhddf8hxj38x74zjxla072wyppej7xv9psfg","id":"897fa3a9-512f-4a3a-a6b6-d7f84d06751b"}}"""

        //Assert.assertEquals(jsonResult, msgObjectMapper.writeValueAsString(msg1))
        //Assert.assertEquals(jsonResult, msgObjectMapper.writeValueAsString(msg2))
    }
}
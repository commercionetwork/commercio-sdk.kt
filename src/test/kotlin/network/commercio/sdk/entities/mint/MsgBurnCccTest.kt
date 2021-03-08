package network.commercio.sdk.entities.burn

import kotlinx.coroutines.runBlocking
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sdk.entities.mint.BurnCcc
import network.commercio.sdk.entities.mint.MsgBurnCcc
import network.commercio.sdk.entities.msgObjectMapper
import network.commercio.sdk.mint.BurnCccHelper
import org.junit.Assert
import org.junit.Test

class MsgBurnCccTest {

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

        val amount = StdCoin(denom = "uccc", amount = "10")
        val id = "897fa3a9-512f-4a3a-a6b6-d7f84d06751b"

        val burnCcc1 = BurnCcc(
            signerDid = wallet.bech32Address,
            amount = amount,
            id = id
        )

        val msg1 = MsgBurnCcc(burnCcc1)
        Assert.assertNotNull(msg1)

        val burnCcc2 = BurnCccHelper.fromWallet(
            wallet = wallet,
            amount = amount,
            id = id
        )

        val msg2 = MsgBurnCcc(burnCcc2)
        Assert.assertNotNull(msg2)
        Assert.assertEquals(msg1.toString(), msg2.toString())

        print(msgObjectMapper.writeValueAsString(msg1))

        val jsonResult =
            """{"type":"commercio/MsgBurnCCC","value":{"amount":{"amount":"10","denom":"uccc"},"id":"897fa3a9-512f-4a3a-a6b6-d7f84d06751b","signer":"did:com:1gkfhddf8hxj38x74zjxla072wyppej7xv9psfg"}}"""

        Assert.assertEquals(jsonResult, msgObjectMapper.writeValueAsString(msg1))
        Assert.assertEquals(jsonResult, msgObjectMapper.writeValueAsString(msg2))
    }
}
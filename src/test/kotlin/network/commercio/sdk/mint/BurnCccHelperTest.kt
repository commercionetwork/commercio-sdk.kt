package network.commercio.sdk.mint

import kotlinx.coroutines.runBlocking
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sdk.entities.mint.BurnCcc

import org.junit.Assert
import org.junit.Test
import java.util.*


class BurnCccHelperTest {
    @Test
    fun `BurnCccHelper fromWallet create correctly BurnCcc`() = runBlocking {
        val networkInfo = NetworkInfo(bech32Hrp = "did:com:", lcdUrl = "")

        val mnemonicString =
            "dash ordinary anxiety zone slot rail flavor tortoise guilt divert pet sound ostrich increase resist short ship lift town ice split payment round apology"
        val mnemonic = mnemonicString.split(" ")
        val wallet = Wallet.derive(mnemonic, networkInfo)

        val amount = StdCoin(denom = "uccc", amount = "10")
        val id = UUID.randomUUID().toString()

        val expectedBurnCcc = BurnCccHelper.fromWallet(
            wallet = wallet,
            amount = amount,
            id = id
        )

        val burnCcc = BurnCcc(
            signerDid = wallet.bech32Address,
            amount = amount,
            id = id
        )

        Assert.assertEquals(expectedBurnCcc.toString(), burnCcc.toString())
    }
}
package network.commercio.sdk.mint

import kotlinx.coroutines.runBlocking
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sdk.entities.mint.OpenCdp
import org.junit.Assert
import org.junit.Test


class OpenCdpHelperTest {
    @Test
    fun `OpenCdpHelper fromWallet create correctly OpenCdp`() = runBlocking {
        val networkInfo = NetworkInfo(bech32Hrp = "did:com:", lcdUrl = "")

        val mnemonicString =
            "dash ordinary anxiety zone slot rail flavor tortoise guilt divert pet sound ostrich increase resist short ship lift town ice split payment round apology"
        val mnemonic = mnemonicString.split(" ")
        val wallet = Wallet.derive(mnemonic, networkInfo)

        val depositAmount = listOf(StdCoin(denom = "commercio", amount = "10"))

        val expectedOpenCdp = OpenCdpHelper.fromWallet(
            wallet = wallet,
            amount = depositAmount
        )

        val openCdp = OpenCdp(
            depositAmount = depositAmount,
            depositorDid = wallet.bech32Address
        )

        Assert.assertEquals(expectedOpenCdp.toString(), openCdp.toString())
    }
}
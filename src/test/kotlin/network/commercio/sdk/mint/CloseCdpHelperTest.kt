package network.commercio.sdk.mint

import kotlinx.coroutines.runBlocking
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.Wallet
import network.commercio.sdk.entities.mint.CloseCdp
import org.junit.Assert
import org.junit.Test


class CloseCdpHelperTest {
    @Test
    fun `CloseCdpHelper fromWallet create correctly CloseCdp`() = runBlocking {
        val networkInfo = NetworkInfo(bech32Hrp = "did:com:", lcdUrl = "")

        val mnemonicString =
            "dash ordinary anxiety zone slot rail flavor tortoise guilt divert pet sound ostrich increase resist short ship lift town ice split payment round apology"
        val mnemonic = mnemonicString.split(" ")
        val wallet = Wallet.derive(mnemonic, networkInfo)

        val timestamp = 725

        val expectedCloseCdp = CloseCdpHelper.fromWallet(
            timeStamp = timestamp,
            wallet = wallet
        )

        val closeCdp = CloseCdp(
            timeStamp = timestamp,
            signerDid = wallet.bech32Address
        )

        Assert.assertEquals(expectedCloseCdp.toString(), closeCdp.toString())
    }
}
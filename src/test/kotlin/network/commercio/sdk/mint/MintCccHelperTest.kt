package network.commercio.sdk.mint

import kotlinx.coroutines.runBlocking
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sdk.entities.mint.MintCcc
import org.junit.Assert
import org.junit.Test


class MintCccHelperTest {
    @Test
    fun `MintCccHelper fromWallet create correctly MintCcc`() = runBlocking {
        val networkInfo = NetworkInfo(bech32Hrp = "did:com:", lcdUrl = "")

        val mnemonicString =
            "dash ordinary anxiety zone slot rail flavor tortoise guilt divert pet sound ostrich increase resist short ship lift town ice split payment round apology"
        val mnemonic = mnemonicString.split(" ")
        val wallet = Wallet.derive(mnemonic, networkInfo)

        val depositAmount = listOf(StdCoin(denom = "uccc", amount = "10"))

        val expectedMintCcc = MintCccHelper.fromWallet(
            wallet = wallet,
            amount = depositAmount,
            id = "f18e7aac-6659-43bd-be26-9842b80d3c29"
        )

        val mintCcc = MintCcc(
            depositorDid = wallet.bech32Address,
            depositAmount = depositAmount,
            id = "f18e7aac-6659-43bd-be26-9842b80d3c29"
        )

        Assert.assertEquals(expectedMintCcc.toString(), mintCcc.toString())
    }
}
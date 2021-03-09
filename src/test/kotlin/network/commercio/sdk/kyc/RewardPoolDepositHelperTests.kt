package network.commercio.sdk.kyc

import kotlinx.coroutines.runBlocking
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sdk.entities.kyc.RewardPoolDeposit
import org.junit.Assert
import org.junit.Test

class RewardPoolDepositHelperTests {

    @Test
    fun `RewardPoolDepositHelper fromWallet create correctly RewardPoolDeposit`() = runBlocking {
        val networkInfo = NetworkInfo(bech32Hrp = "did:com:", lcdUrl = "")
        val mnemonicString =
            "dash ordinary anxiety zone slot rail flavor tortoise guilt divert pet sound ostrich increase resist short ship lift town ice split payment round apology"
        val mnemonic = mnemonicString.split(" ")
        val wallet = Wallet.derive(mnemonic, networkInfo)
        val depositAmount = listOf(StdCoin(amount = "10", denom = "uccc"))

        val expectedReward = RewardPoolDeposit(
            depositAmount = depositAmount,
            depositorDid = wallet.bech32Address
        )

        val reward = RewardPoolDepositHelper.fromWallet(
            wallet = wallet,
            amount = depositAmount
        )

        Assert.assertEquals(expectedReward.toString(), reward.toString())
    }
}
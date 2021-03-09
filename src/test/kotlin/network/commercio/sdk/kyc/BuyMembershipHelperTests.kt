package network.commercio.sdk.kyc

import kotlinx.coroutines.runBlocking
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.Wallet
import network.commercio.sdk.entities.kyc.BuyMembership
import network.commercio.sdk.entities.kyc.MembershipType
import org.junit.Assert
import org.junit.Test
import org.kethereum.bip39.generateMnemonic
import org.kethereum.bip39.wordlists.WORDLIST_ENGLISH


class BuyMembershipHelperTest {
    @Test
    fun `BuyMembershipHelper fromWallet create correctly BuyMembership`() = runBlocking {
        val networkInfo = NetworkInfo(bech32Hrp = "did:com:", lcdUrl = "")

        val newUserMnemonic = generateMnemonic(strength = 256, wordList = WORDLIST_ENGLISH).split(" ")
        val newUserWallet = Wallet.derive(newUserMnemonic, networkInfo)
        val walletTsp = "did:com:id"

        val expectedBuyMembership = BuyMembership(
            membershipType = MembershipType.GOLD,
            buyerDid = newUserWallet.bech32Address,
            tsp = walletTsp
        )

        val buyMembership = BuyMembershipHelper.fromWallet(
            wallet = newUserWallet,
            membershipType = MembershipType.GOLD,
            tsp = walletTsp
        )

        Assert.assertEquals(expectedBuyMembership.toString(), buyMembership.toString())
    }
}
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

        val mnemonicTsp = generateMnemonic(strength = 256, wordList = WORDLIST_ENGLISH).split(" ")
        val walletTsp = Wallet.derive(newUserMnemonic, networkInfo)

        val expectedBuyMembership = BuyMembership(
            membershipType = MembershipType.GOLD,
            buyerDid = newUserWallet.bech32Address,
            tsp = walletTsp.bech32Address
        )

        val buyMembership = BuyMembershipHelper.fromWallet(
            wallet = newUserWallet,
            membershipType = MembershipType.GOLD,
            tsp = walletTsp.bech32Address
        )

        Assert.assertEquals(expectedBuyMembership.toString(), buyMembership.toString())
    }
}
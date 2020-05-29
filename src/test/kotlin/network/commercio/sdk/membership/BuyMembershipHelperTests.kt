package network.commercio.sdk.membership

import kotlinx.coroutines.runBlocking
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.Wallet
import network.commercio.sdk.entities.membership.BuyMembership
import network.commercio.sdk.entities.membership.MembershipType
import org.junit.Assert
import org.junit.Test
import org.kethereum.bip39.generateMnemonic
import org.kethereum.bip39.wordlists.WORDLIST_ENGLISH


class BuyMembershipHelperTest {
    @Test
    fun `BuyMembershipHelper fromWallet create correctly BuyMembership`() = runBlocking {
        val chain = NetworkInfo(bech32Hrp = "did:com:", lcdUrl = "")

        val newUserMnemonic = generateMnemonic(strength = 256, wordList = WORDLIST_ENGLISH).split(" ")
        val newUserWallet = Wallet.derive(newUserMnemonic, chain)

        val buyMembership1: BuyMembership = BuyMembership(
            membershipType = MembershipType.GOLD,
            buyerDid = newUserWallet.bech32Address
        )

        val buyMembership2 = BuyMembershipHelper.fromWallet(newUserWallet, MembershipType.GOLD)

        print(buyMembership1)
        print(buyMembership2)
        Assert.assertEquals(buyMembership1.toString(), buyMembership2.toString())
    }
}
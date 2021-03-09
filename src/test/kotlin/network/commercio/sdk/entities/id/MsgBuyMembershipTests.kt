package network.commercio.sdk.entities.id

import kotlinx.coroutines.runBlocking
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.Wallet
import network.commercio.sdk.entities.kyc.BuyMembership
import network.commercio.sdk.entities.kyc.MembershipType
import network.commercio.sdk.entities.kyc.MsgBuyMembership
import network.commercio.sdk.entities.msgObjectMapper
import network.commercio.sdk.kyc.BuyMembershipHelper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.kethereum.bip39.generateMnemonic
import org.kethereum.bip39.wordlists.WORDLIST_ENGLISH

class BuyMembershipTest {

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
        val walletTsp = "did:com:1ckfsy552uvwskw9yt3awh9a2u0j423t3jk8u06"

        val buyMembership1 = BuyMembership(
            membershipType = MembershipType.GOLD,
            buyerDid = wallet.bech32Address,
            tsp = walletTsp
        )
        val msg1 = MsgBuyMembership(buyMembership1)
        assertNotNull(msg1)

        val buyMembership2 = BuyMembershipHelper.fromWallet(
            wallet = wallet,
            membershipType = MembershipType.GOLD,
            tsp = walletTsp
        )
        val msg2 = MsgBuyMembership(buyMembership2)
        assertNotNull(msg2)

        assertEquals(msg1.toString(), msg2.toString())

        val jsonResult =
            """{"type":"commercio/MsgBuyMembership","value":{"buyer":"did:com:1gkfhddf8hxj38x74zjxla072wyppej7xv9psfg","membership_type":"gold","tsp":"did:com:1ckfsy552uvwskw9yt3awh9a2u0j423t3jk8u06"}}"""
        assertEquals(jsonResult, msgObjectMapper.writeValueAsString(msg1))
        assertEquals(jsonResult, msgObjectMapper.writeValueAsString(msg2))
    }
}

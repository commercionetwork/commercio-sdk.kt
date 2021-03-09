package network.commercio.sdk.entities.id

import kotlinx.coroutines.runBlocking
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.Wallet
import network.commercio.sdk.entities.membership.BuyMembership
import network.commercio.sdk.entities.membership.MembershipType
import network.commercio.sdk.entities.membership.MsgBuyMembership
import network.commercio.sdk.entities.msgObjectMapper
import network.commercio.sdk.kyc.BuyMembershipHelper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

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


        val buyMembership1: BuyMembership = BuyMembership(
            membershipType = MembershipType.GOLD,
            buyerDid = wallet.bech32Address
        )
        val msg1 = MsgBuyMembership(buyMembership1)
        assertNotNull(msg1)

        val buyMembership2 = BuyMembershipHelper.fromWallet(wallet, MembershipType.GOLD)
        val msg2 = MsgBuyMembership(buyMembership2)
        assertNotNull(msg2)

        assertEquals(msg1.toString(), msg2.toString())

        val jsonResult =
            """{"type":"commercio/MsgBuyMembership","value":{"buyer":"did:com:1gkfhddf8hxj38x74zjxla072wyppej7xv9psfg","membership_type":"gold"}}"""
        assertEquals(jsonResult, msgObjectMapper.writeValueAsString(msg1))
        assertEquals(jsonResult, msgObjectMapper.writeValueAsString(msg2))
    }
}

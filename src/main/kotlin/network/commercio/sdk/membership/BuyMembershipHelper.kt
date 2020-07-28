package network.commercio.sdk.membership

import network.commercio.sacco.Wallet
import network.commercio.sdk.entities.membership.BuyMembership
import network.commercio.sdk.entities.membership.MembershipType

/**
 * Allows to easily create a BuyMembership and perform common related operations.
 */
object BuyMembershipHelper {

    /**
     * Creates a BuyMembership from the given [wallet] and [membershipType].
     */
    fun fromWallet(
        wallet: Wallet,
        membershipType: MembershipType
    ): BuyMembership {
        return BuyMembership(
            membershipType = membershipType,
            buyerDid = wallet.bech32Address
        )
    }
}

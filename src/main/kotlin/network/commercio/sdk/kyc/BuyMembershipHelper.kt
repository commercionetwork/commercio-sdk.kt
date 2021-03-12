package network.commercio.sdk.kyc

import network.commercio.sacco.Wallet
import network.commercio.sdk.entities.kyc.BuyMembership
import network.commercio.sdk.entities.kyc.MembershipType

/**
 * Allows to easily create a BuyMembership and perform common related operations.
 */
object BuyMembershipHelper {

    /**
     * Creates a BuyMembership from the given [wallet],
     * [membershipType] and [tsp] address.
     */
    fun fromWallet(
        wallet: Wallet,
        membershipType: MembershipType,
        tsp: String
    ): BuyMembership {
        return BuyMembership(
            membershipType = membershipType,
            buyerDid = wallet.bech32Address,
            tsp = tsp
        )
    }
}

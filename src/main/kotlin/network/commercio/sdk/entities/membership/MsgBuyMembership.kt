package network.commercio.sdk.entities.membership

import network.commercio.sacco.models.types.StdMsg

/**
 * Represents the transaction message that must be used when wanting to buy a membership.
 */
data class MsgBuyMembership(
    private val membershipType: MembershipType,
    private val buyerDid: String
) : StdMsg(
    type = "commercio/MsgBuyMembership",
    value = mapOf(
        "membership_type" to membershipType.toString().toLowerCase(),
        "buyer" to buyerDid
    )
)
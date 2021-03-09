package network.commercio.sdk.entities.kyc

import network.commercio.sacco.models.types.StdMsg

/**
 * Represents the transaction message that must be used when wanting to buy a membership.
 */
data class MsgBuyMembership(
    private val buyMembership: BuyMembership
) : StdMsg(
    type = "commercio/MsgBuyMembership",
    value = mapOf(
        "membership_type" to buyMembership.membershipType.toString().toLowerCase(),
        "buyer" to buyMembership.buyerDid,
        "tsp" to buyMembership.tsp
    )
)
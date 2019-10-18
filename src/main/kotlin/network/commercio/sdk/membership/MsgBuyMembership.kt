package network.commercio.sdk.membership

import network.commercio.sacco.models.types.StdMsg

data class MsgBuyMembership(
    val membershipType: MembershipType,
    val buyerDid: String
) : StdMsg(
    type = "commercio/MsgBuyMembership",
    value = mapOf(
        "membership_type" to membershipType.toString().toLowerCase(),
        "buyer" to buyerDid
    )
)
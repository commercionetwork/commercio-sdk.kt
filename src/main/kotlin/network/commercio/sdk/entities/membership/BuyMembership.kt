package network.commercio.sdk.entities.membership

/**
 * Contains the data related to a membership buying.
 */
data class BuyMembership(
    internal val membershipType: MembershipType,
    internal val buyerDid: String
)
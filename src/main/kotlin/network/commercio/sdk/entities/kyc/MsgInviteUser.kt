package network.commercio.sdk.entities.kyc

import network.commercio.sacco.models.types.StdMsg

/**
 * Represents the transaction message that must be used when wanting to invite a new user to join the system
 * and being recognized as his invitee. After doing so, if the invited user buys a membership, you will be
 * able to get a reward based on your current membership and the type he has bought.
 */
data class MsgInviteUser(
    private val inviteUser: InviteUser
) : StdMsg(
    type = "commercio/MsgInviteUser",
    value = mapOf(
        "recipient" to inviteUser.recipientDid,
        "sender" to inviteUser.senderDid
    )
)
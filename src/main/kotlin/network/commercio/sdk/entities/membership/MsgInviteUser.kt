package network.commercio.sdk.entities.membership

import network.commercio.sacco.models.types.StdMsg

/**
 * Represents the transaction message that must be used when wanting to invite a new user to join the system
 * and being recognized as his invitee. After doing so, if the invited user buys a membership, you will be
 * able to get a reward based on your current membership and the type he has bought.
 */
data class MsgInviteUser(
    private val recipientDid: String,
    private val senderDid: String
) : StdMsg(
    type = "commercio/MsgInviteUser",
    value = mapOf(
        "recipient" to recipientDid,
        "sender" to senderDid
    )
)
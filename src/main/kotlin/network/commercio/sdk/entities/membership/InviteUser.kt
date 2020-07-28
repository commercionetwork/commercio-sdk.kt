package network.commercio.sdk.entities.membership

/**
 * Contains the data related to invite a new user to join the system.
 */
data class InviteUser(
    val recipientDid: String,
    val senderDid: String
)
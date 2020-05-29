package network.commercio.sdk.membership

import network.commercio.sacco.Wallet
import network.commercio.sdk.entities.id.Did
import network.commercio.sdk.entities.membership.InviteUser

/**
 * Allows to easily create an InviteUser and perform common related operations.
 */
object InviteUserHelper {

    /**
     * Creates an InviteUser from the given [wallet] and [user].
     */
    fun fromWallet(
        wallet: Wallet,
        user: Did
    ): InviteUser {
        return InviteUser(
            recipientDid = user.value,
            senderDid = wallet.bech32Address
        )
    }
}

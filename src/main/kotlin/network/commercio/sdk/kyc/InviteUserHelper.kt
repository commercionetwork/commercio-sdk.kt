package network.commercio.sdk.kyc

import network.commercio.sacco.Wallet
import network.commercio.sdk.entities.id.Did
import network.commercio.sdk.entities.membership.InviteUser

/**
 * Allows to easily create an InviteUser and perform common related operations.
 */
object InviteUserHelper {

    /**
     * Creates an InviteUser from the given [wallet] and [recipientDid].
     */
    fun fromWallet(
        wallet: Wallet,
        recipientDid: Did
    ): InviteUser {
        return InviteUser(
            recipientDid = recipientDid.value,
            senderDid = wallet.bech32Address
        )
    }
}

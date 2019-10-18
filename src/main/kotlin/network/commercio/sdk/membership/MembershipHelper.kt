package network.commercio.sdk.membership

import network.commercio.sacco.Wallet
import network.commercio.sdk.crypto.TxHelper
import network.commercio.sdk.entities.Did

/**
 * Allows to easily perform CommercioMEMBERSHIP related operations.
 */
object MembershipHelper {

    /**
     * Invites the given [user].
     * @return the tx hash if everything was ok.
     */
    suspend fun inviteUser(user: Did, wallet: Wallet): String {
        val msg = MsgInviteUser(recipientDid = user.value, senderDid = wallet.bech32Address)
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet)
    }

    /**
     * Buys the membership with the given [membershipType].
     * @return the tx has if everything was ok.
     */
    suspend fun buyMembership(membershipType: MembershipType, wallet: Wallet): String {
        val msg = MsgBuyMembership(membershipType = membershipType, buyerDid = wallet.bech32Address)
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet)
    }

}
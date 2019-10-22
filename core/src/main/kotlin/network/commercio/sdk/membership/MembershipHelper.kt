package network.commercio.sdk.membership

import network.commercio.sacco.TxResponse
import network.commercio.sacco.Wallet
import network.commercio.sdk.entities.id.Did
import network.commercio.sdk.tx.TxHelper

/**
 * Allows to easily perform CommercioMEMBERSHIP related operations.
 */
object MembershipHelper {

    /**
     * Invites the given [user].
     * @return the tx hash if everything was ok.
     */
    suspend fun inviteUser(user: Did, wallet: Wallet): TxResponse {
        val msg = MsgInviteUser(recipientDid = user.value, senderDid = wallet.bech32Address)
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet)
    }

    /**
     * Buys the membership with the given [membershipType].
     * @return the tx has if everything was ok.
     */
    suspend fun buyMembership(membershipType: MembershipType, wallet: Wallet): TxResponse {
        val msg = MsgBuyMembership(membershipType = membershipType, buyerDid = wallet.bech32Address)
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet)
    }

}
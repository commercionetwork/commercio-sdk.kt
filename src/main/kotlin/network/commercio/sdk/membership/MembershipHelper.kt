package network.commercio.sdk.membership

import network.commercio.sacco.TxResponse
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sacco.models.types.StdFee
import network.commercio.sdk.entities.id.Did
import network.commercio.sdk.entities.membership.MembershipType
import network.commercio.sdk.entities.membership.MsgBuyMembership
import network.commercio.sdk.entities.membership.MsgInviteUser
import network.commercio.sdk.tx.TxHelper

/**
 * Allows to easily perform CommercioMEMBERSHIP related operations.
 */
object MembershipHelper {

    /**
     * Sends a new transaction in order to invite the given [user].
     */
    suspend fun inviteUser(
        user: Did,
        wallet: Wallet,
        fee: StdFee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "10000")))
    ): TxResponse {
        val msg = MsgInviteUser(
            recipientDid = user.value,
            senderDid = wallet.bech32Address
        )
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet, fee = fee)
    }

    /**
     * Buys the membership with the given [membershipType].
     */
    suspend fun buyMembership(
        membershipType: MembershipType,
        wallet: Wallet,
        fee: StdFee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "10000")))
    ): TxResponse {
        val msg = MsgBuyMembership(
            membershipType = membershipType,
            buyerDid = wallet.bech32Address
        )
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet, fee = fee)
    }
}
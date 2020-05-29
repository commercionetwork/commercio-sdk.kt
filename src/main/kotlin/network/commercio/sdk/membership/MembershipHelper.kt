package network.commercio.sdk.membership

import network.commercio.sacco.TxResponse
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdFee
import network.commercio.sdk.entities.id.Did
import network.commercio.sdk.entities.membership.*
import network.commercio.sdk.tx.TxHelper
import network.commercio.sdk.tx.TxHelper.BroadcastingMode

/**
 * Allows to easily perform CommercioMEMBERSHIP related operations.
 */
object MembershipHelper {

    init {
        System.setProperty("javax.net.ssl.trustStoreType", "JKS")
    }

    /**
     * Sends a new transaction in order to invite the given [user].
     * Optionally [fee] and broadcasting [mode] parameters can be specified.
     */
    suspend fun inviteUser(
        user: Did,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {
        val msg = MsgInviteUser(
            inviteUser = InviteUser(
                recipientDid = user.value,
                senderDid = wallet.bech32Address
            )
        )
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet, fee = fee, mode = mode)
    }

    /**
     * Sends a new transaction in order to invite the given [inviteUsers] users list.
     * Optionally [fee] and broadcasting [mode] parameters can be specified.
     */
    suspend fun inviteUsersList(
        inviteUsers: List<InviteUser>,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {
        val msgs = inviteUsers.map { MsgInviteUser(it) }
        return TxHelper.createSignAndSendTx(msgs = msgs, wallet = wallet, fee = fee, mode = mode)
    }

    /**
     * Buys the membership with the given [membershipType].
     * Optionally [fee] and broadcasting [mode] parameters can be specified.
     */
    suspend fun buyMembership(
        membershipType: MembershipType,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {
        val msg = MsgBuyMembership(
            buyMembership = BuyMembership(
                membershipType = membershipType,
                buyerDid = wallet.bech32Address
            )
        )
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet, fee = fee, mode = mode)
    }

    /**
     * Buys the membership with the given [buyMemberships] memberships list.
     * Optionally [fee] and broadcasting [mode] parameters can be specified.
     */
    suspend fun buyMembershipsList(
        buyMemberships: List<BuyMembership>,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {
        val msgs = buyMemberships.map { MsgBuyMembership(it) }
        return TxHelper.createSignAndSendTx(msgs = msgs, wallet = wallet, fee = fee, mode = mode)
    }
}
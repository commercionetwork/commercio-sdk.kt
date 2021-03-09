package network.commercio.sdk.kyc

import network.commercio.sacco.TxResponse
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdFee
import network.commercio.sdk.entities.id.Did
import network.commercio.sdk.entities.kyc.*
import network.commercio.sdk.tx.TxHelper
import network.commercio.sdk.tx.TxHelper.BroadcastingMode

/**
 * Allows to easily perform Commercio KYC module related operations.
 */
object KycHelper {

    init {
        System.setProperty("javax.net.ssl.trustStoreType", "JKS")
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

    /**
     * Deposit a list of [rewardPoolDeposits] deposits into reward pool
     * with the depositor [wallet].
     * Optionally [fee] and broadcasting [mode] parameters can be specified.
     */
    suspend fun rewardPoolDepositsList(
        rewardPoolDeposits: List<RewardPoolDeposit>,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {
        val msgs = rewardPoolDeposits.map { MsgRewardPoolDeposit(it) }
        return TxHelper.createSignAndSendTx(msgs = msgs, wallet = wallet, fee = fee, mode = mode)
    }
}
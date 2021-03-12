package network.commercio.sdk.kyc

import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sdk.entities.kyc.RewardPoolDeposit

/**
 * Allows to easily create an DepositRewardPool and perform common related operations.
 */
object RewardPoolDepositHelper {
    /**
     * Creates an RewardPoolDeposit from the given [wallet] and deposit [amount].
     */
    fun fromWallet(
        wallet: Wallet,
        amount: List<StdCoin>
    ): RewardPoolDeposit {
        return RewardPoolDeposit(
            depositAmount = amount,
            depositorDid = wallet.bech32Address
        )
    }
}
package network.commercio.sdk.entities.kyc

import network.commercio.sacco.models.types.StdMsg

/**
 * Represents the transaction message that must be used
 * when wanting to deposit into reward pool.
 */
data class MsgRewardPoolDeposit(
    private val rewardPoolDeposit: RewardPoolDeposit
) : StdMsg(
    type = "commercio/MsgDepositIntoLiquidityPool",
    value = mapOf(
        "depositor" to rewardPoolDeposit.depositorDid,
        "amount" to rewardPoolDeposit.depositAmount
    )

)


//data class MsgInviteUser(
//    private val inviteUser: InviteUser
//) : StdMsg(
//    type = "commercio/MsgInviteUser",
//    value = mapOf(
//        "recipient" to inviteUser.recipientDid,
//        "sender" to inviteUser.senderDid
//    )
//)
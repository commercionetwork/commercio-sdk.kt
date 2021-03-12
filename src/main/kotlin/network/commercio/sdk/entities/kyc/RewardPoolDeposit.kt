package network.commercio.sdk.entities.kyc

import network.commercio.sacco.models.types.StdCoin

data class RewardPoolDeposit(
    val depositAmount: List<StdCoin>,
    val depositorDid: String
)
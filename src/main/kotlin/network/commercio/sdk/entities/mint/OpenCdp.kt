package network.commercio.sdk.entities.mint

import network.commercio.sacco.models.types.StdCoin

data class OpenCdp(
    val depositAmount: List<StdCoin>,
    val depositorDid: String
)
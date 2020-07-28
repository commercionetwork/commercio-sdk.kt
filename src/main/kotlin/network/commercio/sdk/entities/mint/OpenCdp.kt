package network.commercio.sdk.entities.mint

import network.commercio.sacco.models.types.StdCoin

/**
 * Contains the data related to a Collateralized Debt Position
 * that is opened by a user.
 */
data class OpenCdp(
    val depositAmount: List<StdCoin>,
    val depositorDid: String
)
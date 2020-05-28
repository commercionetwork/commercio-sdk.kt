package network.commercio.sdk.entities.id

import network.commercio.sacco.models.types.StdCoin

/**
 * Represents the object used to build the transaction message for asking a private Did power up.
 */
data class RequestDidPowerUp(
    val claimantDid: String,
    val amount: List<StdCoin>,
    val powerUpProof: String,
    val uuid: String,
    val proofKey: String
)
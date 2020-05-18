package network.commercio.sdk.entities.id

import network.commercio.sacco.models.types.StdCoin
import network.commercio.sacco.models.types.StdMsg

/**
 * Represents the transaction message that should be used when asking for a private Did power up.
 */
data class MsgRequestDidPowerUp(
    private val claimantDid: String,
    private val amount: List<StdCoin>,
    private val powerUpProof: String,
    private val uuid: String,
    private val proofKey: String
) : StdMsg(
    type = "commercio/MsgRequestDidPowerUp",
    value = mapOf(
        "claimant" to claimantDid,
        "amount" to amount,
        "proof" to powerUpProof,
        "id" to uuid,
        "proof_key" to proofKey
    )
)
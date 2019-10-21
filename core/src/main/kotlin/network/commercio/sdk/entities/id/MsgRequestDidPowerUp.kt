package network.commercio.sdk.entities.id

import network.commercio.sacco.models.types.StdMsg

/**
 * Represents the transaction message that should be used when asking for a private Did power up.
 */
data class MsgRequestDidPowerUp(
    private val claimantDid: String,
    private val amount: String,
    private val powerUpProof: String,
    private val encryptionKey: String
) : StdMsg(
    type = "commercio/MsgRequestDidPowerUp",
    value = mapOf(
        "claimant" to claimantDid,
        "amount" to amount,
        "proof" to powerUpProof,
        "encryption_key" to encryptionKey
    )
)
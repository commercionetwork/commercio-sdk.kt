package network.commercio.sdk.entities.id

import network.commercio.sacco.models.types.StdCoin
import network.commercio.sacco.models.types.StdMsg

/**
 * Represents the message that should be included into a transaction when wanting to deposit
 * a specific amount of tokens that can later be used to power up private DIDs.
 */
data class MsgRequestDidDeposit(
    private val recipientDid: String,
    private val amount: List<StdCoin>,
    private val depositProof: String,
    private val encryptionKey: String,
    private val senderDid: String
) : StdMsg(
    type = "commercio/MsgRequestDidDeposit",
    value = mapOf(
        "recipient" to recipientDid,
        "amount" to amount,
        "proof" to depositProof,
        "encryption_key" to encryptionKey,
        "from_address" to senderDid
    )
)
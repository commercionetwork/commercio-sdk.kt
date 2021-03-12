package network.commercio.sdk.entities.mint

import network.commercio.sacco.models.types.StdMsg

/**
 * Represents the transaction message that must be used
 * to mint CCC.
 */
data class MsgMintCcc(
    private val mintCcc: MintCcc
) : StdMsg(
    type = "commercio/MsgMintCCC", value = mapOf(
        "depositor" to mintCcc.depositorDid,
        "deposit_amount" to mintCcc.depositAmount,
        "id" to mintCcc.id
    )
)
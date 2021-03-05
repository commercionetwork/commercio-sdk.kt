package network.commercio.sdk.entities.mint

import network.commercio.sacco.models.types.StdMsg

/**
 * Represents the transaction message that must be used
 * to burn previously minteted CCC.
 */
data class MsgBurnCcc(
    private val burnCcc: BurnCcc
) : StdMsg(
    type = "commercio/MsgBurnCCC", value = mapOf(
        "signer" to burnCcc.signerDid,
        "amount" to burnCcc.amount,
        "id" to burnCcc.id
    )
)
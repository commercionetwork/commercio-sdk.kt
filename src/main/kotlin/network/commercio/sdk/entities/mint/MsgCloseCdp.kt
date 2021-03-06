package network.commercio.sdk.entities.mint

import network.commercio.sacco.models.types.StdMsg

/**
 * Represents the transaction message that must be used when wanting to close a previously opened
 * Collateralized Debt position to get back the Commercio Tokens that have been locked with it.
 */
data class MsgCloseCdp(
    private val closeCdp: CloseCdp
) : StdMsg(
    type = "commercio/MsgCloseCdp", value = mapOf(
        "signer" to closeCdp.signerDid,
        "cdp_timestamp" to closeCdp.timeStamp.toString()
    )
)
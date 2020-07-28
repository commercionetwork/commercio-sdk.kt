package network.commercio.sdk.entities.mint

import network.commercio.sacco.models.types.StdMsg

/**
 * Represents the transaction message that must be used when wanting to open a Collateralized Debt position that
 * allows to transform the user's Commercio Token into Commercio Cash Credits.
 */
data class MsgOpenCdp(
    private val openCdp: OpenCdp
) : StdMsg(
    type = "commercio/MsgOpenCdp", value = mapOf(
        "deposit_amount" to openCdp.depositAmount,
        "depositor" to openCdp.depositorDid
    )
)
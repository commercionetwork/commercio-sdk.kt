package network.commercio.sdk.entities.mint

import network.commercio.sacco.models.types.StdCoin
import network.commercio.sdk.utils.matchBech32Format
import network.commercio.sdk.utils.matchUuidv4

/**
 * Contains the data to burn previously minteted CCC.
 */
data class BurnCcc(
    val signerDid: String,
    val amount: StdCoin,
    val id: String
) {
    init {
        require(matchBech32Format(signerDid)) { "signer requires a valid Bech32 format" }
        require(matchUuidv4(id)) { "id requires a valid UUID v4 format" }
    }
}
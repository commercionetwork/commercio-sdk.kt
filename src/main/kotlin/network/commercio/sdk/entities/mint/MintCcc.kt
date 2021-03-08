package network.commercio.sdk.entities.mint

import network.commercio.sacco.models.types.StdCoin
import network.commercio.sdk.utils.matchBech32Format
import network.commercio.sdk.utils.matchUuidv4

/**
 * Contains the data to mint CCC.
 */
data class MintCcc(
    val depositAmount: List<StdCoin>,
    val depositorDid: String,
    val id: String
) {
    init {
        require(matchBech32Format(depositorDid)) { "depositor requires a valid Bech32 format" }
        require(matchUuidv4(id)) { "id requires a valid UUID v4 format" }
    }
}
package network.commercio.sdk.mint

import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sdk.entities.mint.BurnCcc

/**
 * Allows to easily create a BurnCcc.
 */
object BurnCccHelper {

    /**
     * Creates a BurnCcc from the given [wallet],
     * [amount] to be burned and mint [id].
     */
    fun fromWallet(
        wallet: Wallet,
        id: String,
        amount: StdCoin
    ): BurnCcc {
        return BurnCcc(
            signerDid = wallet.bech32Address,
            id = id,
            amount = amount
        )
    }
}
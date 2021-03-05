package network.commercio.sdk.mint

import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sdk.entities.mint.MintCcc

/**
 * Allows to easily create a MintCcc.
 */
object MintCccHelper {

    /**
     * Creates an MintCcc from the given [wallet],
     * deposit [amount] and mint [id].
     */
    fun fromWallet(
        wallet: Wallet,
        amount: List<StdCoin>,
        id: String
    ): MintCcc {
        return MintCcc(depositAmount = amount, depositorDid = wallet.bech32Address, id = id)
    }
}
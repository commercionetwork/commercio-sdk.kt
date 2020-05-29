package network.commercio.sdk.mint

import network.commercio.sacco.Wallet
import network.commercio.sdk.entities.mint.CloseCdp

/**
 * Allows to easily create a CloseCdp and perform common related operations
 */
object CloseCdpHelper {

    /**
     * Creates a CloseCdp from the given [wallet] and [timeStamp].
     * [timeStamp] is the 'height' at which the position was opened
     */
    fun fromWallet(
        timeStamp: Int,
        wallet: Wallet
    ): CloseCdp {
        return CloseCdp(signerDid = wallet.bech32Address, timeStamp = timeStamp)
    }
}
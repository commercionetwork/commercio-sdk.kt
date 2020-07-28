package network.commercio.sdk.mint

import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sdk.entities.mint.OpenCdp

/**
 * Allows to easily create a OpenCdp and perform common related operations
 */
object OpenCdpHelper {

    /**
     * Creates an OpenCdp from the given [wallet] and deposit [amount].
     */
    fun fromWallet(
        wallet: Wallet,
        amount: List<StdCoin>
    ): OpenCdp {
        return OpenCdp(depositAmount = amount, depositorDid = wallet.bech32Address)
    }
}
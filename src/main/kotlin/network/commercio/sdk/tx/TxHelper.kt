package network.commercio.sdk.tx

import network.commercio.sacco.*
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sacco.models.types.StdFee
import network.commercio.sacco.models.types.StdMsg

/**
 * Allows to easily perform common transaction operations.
 */
object TxHelper {

    private val defaultGas="200000"
    private val defaultDenom = "ucommercio"
    private val defaultAmount = "10000"

    /**
     * Creates a transaction having the given [msgs], signs it with the given [Wallet] and
     * sends it to the blockchain.
     * Optional parameters can be [fee] and broadcasting [mode], that can be of type "sync", "async" or "block".
     */
    @JvmOverloads
    suspend fun createSignAndSendTx(
        msgs: List<StdMsg>,
        wallet: Wallet,
        fee: StdFee?= null,
        mode: String="sync"

    ): TxResponse {
        val fees=  when(fee) {
            null -> StdFee(gas = defaultGas, amount = listOf(StdCoin(denom = defaultDenom, amount = defaultAmount)))
            else -> fee
        }

        val stdTx = TxBuilder.buildStdTx(stdMsgs = msgs, fee = fees)
        val signedTx = TxSigner.signStdTx(stdTx = stdTx, wallet = wallet)
        val broadcastedStdTx = TxSender.broadcastStdTx(stdTx = signedTx, wallet = wallet, mode = mode )
        return broadcastedStdTx
    }
}
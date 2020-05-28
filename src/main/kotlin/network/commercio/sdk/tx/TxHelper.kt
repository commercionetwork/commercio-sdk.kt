package network.commercio.sdk.tx

import network.commercio.sacco.*
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sacco.models.types.StdFee
import network.commercio.sacco.models.types.StdMsg

/**
 * Allows to easily perform common transaction operations.
 */
object TxHelper {

    private val defaultGas = "100000"
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
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {

        val _fee = when (fee) {
            null -> {
                val gas = when (msgs.size > 1) {
                    true -> (defaultGas.toLong() * msgs.size).toString()
                    else -> defaultGas
                }

                val amount = when (msgs.size > 1) {
                    true -> (defaultAmount.toLong() * msgs.size).toString()
                    else -> defaultAmount
                }
                StdFee(gas = gas, amount = listOf(StdCoin(denom = defaultDenom, amount = amount)))
            }
            else -> fee
        }

        val _mode = when (mode) {
            null -> BroadcastingMode.SYNC.toString()
            else -> mode.toString()
        }

        val stdTx = TxBuilder.buildStdTx(stdMsgs = msgs, fee = _fee)
        val signedTx = TxSigner.signStdTx(stdTx = stdTx, wallet = wallet)
        val broadcastedStdTx = TxSender.broadcastStdTx(stdTx = signedTx, wallet = wallet, mode = _mode)
        return broadcastedStdTx
    }

    enum class BroadcastingMode {
        ASYNC,
        BLOCK,
        SYNC;

        override fun toString(): String {
            return when (this) {
                ASYNC -> "async"
                BLOCK -> "block"
                SYNC -> "sync"
            }
        }
    }
}
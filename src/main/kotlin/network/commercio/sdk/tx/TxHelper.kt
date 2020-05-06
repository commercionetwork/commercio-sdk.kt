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
     * Creates a transaction having the given [msgs] and [fee] inside, signs it with the given [Wallet] and
     * sends it to the blockchain.
     */
    @JvmOverloads
    suspend fun createSignAndSendTx(
        msgs: List<StdMsg>,
        wallet: Wallet,
        fee: StdFee?
    ): TxResponse {
        val fees=  when(fee) {
            null -> StdFee(gas = defaultGas, amount = listOf(StdCoin(denom = defaultDenom, amount = defaultAmount)))
            else -> fee
        }
        val stdTx = TxBuilder.buildStdTx(stdMsgs = msgs, fee = fees)
        val signedTx = TxSigner.signStdTx(stdTx = stdTx, wallet = wallet)
        return TxSender.broadcastStdTx(stdTx = signedTx, wallet = wallet, mode = "block")
    }
}

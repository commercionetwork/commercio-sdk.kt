package network.commercio.sdk.tx

import network.commercio.sacco.*
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sacco.models.types.StdFee
import network.commercio.sacco.models.types.StdMsg

/**
 * Allows to easily perform common transaction operations.
 */
object TxHelper {

    /**
     * Creates a transaction having the given [msgs] and [fee] inside, signs it with the given [Wallet] and
     * sends it to the blockchain.
     */
    @JvmOverloads
    suspend fun createSignAndSendTx(
        msgs: List<StdMsg>,
        fee: StdFee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "1000"))),
        wallet: Wallet
    ): TxResponse {
        val stdTx = TxBuilder.buildStdTx(stdMsgs = msgs, fee = fee)
        val signedTx = TxSigner.signStdTx(stdTx = stdTx, wallet = wallet)
        return TxSender.broadcastStdTx(stdTx = signedTx, wallet = wallet)
    }
}

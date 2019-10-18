package network.commercio.sdk.crypto

import network.commercio.sacco.TxBuilder
import network.commercio.sacco.TxSender
import network.commercio.sacco.TxSigner
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sacco.models.types.StdFee
import network.commercio.sacco.models.types.StdMsg

object TxHelper {

    @JvmOverloads
    suspend fun createSignAndSendTx(
        msgs: List<StdMsg>,
        fee: StdFee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "1000"))),
        wallet: Wallet
    ): String {
        val stdTx = TxBuilder.buildStdTx(stdMsgs = msgs, fee = fee)
        val signedTx = TxSigner.signStdTx(stdTx = stdTx, wallet = wallet)
        return TxSender.broadcastStdTx(stdTx = signedTx, wallet = wallet)
    }
}
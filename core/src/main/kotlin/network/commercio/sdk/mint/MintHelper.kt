package network.commercio.sdk.mint

import network.commercio.sacco.TxResponse
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sdk.entities.mint.MsgCloseCdp
import network.commercio.sdk.entities.mint.MsgOpenCdp
import network.commercio.sdk.tx.TxHelper
import java.text.SimpleDateFormat
import java.util.*

/**
 * Allows to easily perform CommercioMINT related transactions.
 */
object MintHelper {

    /**
     * Opens a new CDP depositing the given [commercioTokenAmount].
     * @return the tx hash if everything is ok.
     */
    suspend fun openCdp(commercioTokenAmount: Int, wallet: Wallet): TxResponse {
        val msg = MsgOpenCdp(
            depositAmount = listOf(StdCoin(denom = "ucommercio", amount = (commercioTokenAmount * 1000000).toString())),
            signerDid = wallet.bech32Address,
            timeStamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault()).format(Date())
        )
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet)
    }

    /**
     * Closes the CDP having the given [timestamp].
     * @return the tx hash if everything went ok.
     */
    suspend fun closeCdp(timestamp: String, wallet: Wallet): TxResponse {
        val msg =
            MsgCloseCdp(timeStamp = timestamp, signerDid = wallet.bech32Address)
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet)
    }

}
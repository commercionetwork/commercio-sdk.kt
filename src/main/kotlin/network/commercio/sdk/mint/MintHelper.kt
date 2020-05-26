package network.commercio.sdk.mint

import network.commercio.sacco.TxResponse
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sacco.models.types.StdFee
import network.commercio.sdk.entities.mint.MsgCloseCdp
import network.commercio.sdk.entities.mint.MsgOpenCdp
import network.commercio.sdk.tx.TxHelper
import network.commercio.sdk.tx.TxHelper.BroadcastingMode

/**
 * Allows to easily perform CommercioMINT related transactions.
 */
object MintHelper {

    /**
     * Opens a new CDP depositing the given [commercioTokenAmount].
     * This will allows the user controlling the given [wallet] to receive half the [commercioTokenAmount] of
     * pico Commercio Cash Credits (`uccc`) into his wallet.
     * Please note that `uccc` are millionth of Commercio Cash Credits and thus to send one document you wil need
     * 10.000 `uccc`.
     */
    @Suppress("EXPERIMENTAL_API_USAGE")
    suspend fun openCdp(
        commercioTokenAmount: ULong,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {

        val msg = MsgOpenCdp(
            depositAmount = listOf(
                StdCoin(
                    denom = "ucommercio",
                    amount = commercioTokenAmount.toString()
                )
            ),
            depositorDid = wallet.bech32Address
        )
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet, fee = fee, mode = mode)
    }

    /**
     * Closes the CDP having the given [timestamp].
     * This will allow the user to trade back the lent amount of pico Commercio Cash Credits (`uccc`) to get the
     * deposited amount of pico Commercio Tokens (`ucommercio`)
     */
    suspend fun closeCdp(
        timestamp: Int,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {
        val msg = MsgCloseCdp(timeStamp = timestamp, signerDid = wallet.bech32Address)
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet, fee = fee,  mode = mode)
    }
}
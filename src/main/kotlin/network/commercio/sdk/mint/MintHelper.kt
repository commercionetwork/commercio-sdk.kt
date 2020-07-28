package network.commercio.sdk.mint

import network.commercio.sacco.TxResponse
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sacco.models.types.StdFee
import network.commercio.sdk.entities.mint.CloseCdp
import network.commercio.sdk.entities.mint.MsgCloseCdp
import network.commercio.sdk.entities.mint.MsgOpenCdp
import network.commercio.sdk.entities.mint.OpenCdp
import network.commercio.sdk.tx.TxHelper
import network.commercio.sdk.tx.TxHelper.BroadcastingMode

/**
 * Allows to easily perform CommercioMINT related transactions.
 */
object MintHelper {

    init {
        System.setProperty("javax.net.ssl.trustStoreType", "JKS")
    }

    /**
     * Opens a new CDP depositing the given [commercioTokenAmount].
     * This will allows the user controlling the given [wallet] to receive half the [commercioTokenAmount] of
     * pico Commercio Cash Credits (`uccc`) into his wallet.
     * Please note that `uccc` are millionth of Commercio Cash Credits and thus to send one document you wil need
     * 10.000 `uccc`.
     * Optionally [fee] and broadcasting [mode] parameters can be specified.
     */
    @Suppress("EXPERIMENTAL_API_USAGE")
    suspend fun openCdp(
        commercioTokenAmount: ULong,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {

        val openCdp = OpenCdp(
            depositAmount = listOf(
                StdCoin(
                    denom = "ucommercio",
                    amount = commercioTokenAmount.toString()
                )
            ),
            depositorDid = wallet.bech32Address
        )

        val msg = MsgOpenCdp(openCdp = openCdp)
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet, fee = fee, mode = mode)
    }

    /**
     * Performs a transaction opening a new CDP [openCdp] as being
     * associated with the address present inside the specified [wallet].
     * Optionally [fee] and broadcasting [mode] parameters can be specified.
     */
    suspend fun openCdp(
        openCdp: OpenCdp,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {
        val msg = MsgOpenCdp(openCdp = openCdp)
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
        val closeCdp = CloseCdp(signerDid = wallet.bech32Address, timeStamp = timestamp)
        val msg = MsgCloseCdp(closeCdp)
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet, fee = fee, mode = mode)
    }


    /**
     * Closes the the open CDPs having the given [closeCdps] list as being
     * associated with the address present inside the specified [wallet].
     * Optionally [fee] and broadcasting [mode] parameters can be specified.
     */
    suspend fun closeCdpsList(
        closeCdps: List<CloseCdp>,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {
        val msgs = closeCdps.map { MsgCloseCdp(it) }
        return TxHelper.createSignAndSendTx(msgs = msgs, wallet = wallet, fee = fee, mode = mode)
    }
}
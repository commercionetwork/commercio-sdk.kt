package network.commercio.sdk.mint

import network.commercio.sacco.TxResponse
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdFee
import network.commercio.sdk.entities.mint.BurnCcc
import network.commercio.sdk.entities.mint.MintCcc
import network.commercio.sdk.entities.mint.MsgBurnCcc
import network.commercio.sdk.entities.mint.MsgMintCcc
import network.commercio.sdk.tx.TxHelper
import network.commercio.sdk.tx.TxHelper.BroadcastingMode

/**
 * Allows to easily perform the Commercio Mint module related transactions.
 */
object MintHelper {

    init {
        System.setProperty("javax.net.ssl.trustStoreType", "JKS")
    }

    /**
     * Mints the CCCs having the given [mintCccs] list as being
     * associated with the address present inside the specified [wallet].
     * Optionally [fee] and broadcasting [mode] parameters can be specified.
     */
    suspend fun mintCccsList(
        mintCccs: List<MintCcc>,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {
        val msgs = mintCccs.map { MsgMintCcc(it) }
        return TxHelper.createSignAndSendTx(msgs = msgs, wallet = wallet, fee = fee, mode = mode)
    }

    /**
     * Burns the CCCs having the given [burnCccs] list as being
     * associated with the address present inside the specified [wallet].
     * Optionally [fee] and broadcasting [mode] parameters can be specified.
     */
    suspend fun burnCccsList(
        burnCccs: List<BurnCcc>,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {
        val msgs = burnCccs.map { MsgBurnCcc(it) }
        return TxHelper.createSignAndSendTx(msgs = msgs, wallet = wallet, fee = fee, mode = mode)
    }
}
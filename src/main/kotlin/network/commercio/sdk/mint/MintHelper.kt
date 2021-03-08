package network.commercio.sdk.mint

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import network.commercio.sacco.TxResponse
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdFee
import network.commercio.sdk.entities.mint.BurnCcc
import network.commercio.sdk.entities.mint.ExchangeTradePosition
import network.commercio.sdk.entities.mint.MintCcc
import network.commercio.sdk.entities.mint.MsgBurnCcc
import network.commercio.sdk.entities.mint.MsgMintCcc
import network.commercio.sdk.networking.Network
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

    /**
     * Returns the list of all the [ExchangeTradePosition]
     * that the specified wallet has minted.
     */
    suspend fun getExchangeTradePositions(
        wallet: Wallet
    ): List<ExchangeTradePosition> {
        val url = "${wallet.networkInfo.lcdUrl}/commerciomint/etps/${wallet.bech32Address}"
        val result = Network.queryChain<Any>(url) as List<*>
        val response = result.map { jacksonObjectMapper().convertValue(it, ExchangeTradePosition::class.java) }.toList()
        return response
    }
}
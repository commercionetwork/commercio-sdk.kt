package network.commercio.sdk.id

import network.commercio.sacco.TxResponse
import network.commercio.sacco.Wallet
import network.commercio.sdk.entities.Did
import network.commercio.sdk.entities.id.DidDocument
import network.commercio.sdk.entities.id.MsgSetDidDocument
import network.commercio.sdk.networking.Network
import network.commercio.sdk.tx.TxHelper
import network.commercio.sdk.utils.tryOrNull

/**
 * Allows to perform common operations related to CommercioID.
 */
object IdHelper {

    suspend fun getDidDocument(did: Did, wallet: Wallet): DidDocument? = tryOrNull {
        Network.query<DidDocument>(url = "${wallet.networkInfo.lcdUrl}/identities/${did.value}")
    }

    /**
     * Performs a transaction setting the specified [didDocument] as being associated with the
     * address present inside the specified [wallet].
     */
    suspend fun setDidDocument(didDocument: DidDocument, wallet: Wallet): TxResponse {
        val msg = MsgSetDidDocument(didDocument)
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet)
    }
}
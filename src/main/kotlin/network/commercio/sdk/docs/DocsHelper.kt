package network.commercio.sdk.docs

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import network.commercio.sacco.TxResponse
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdFee
import network.commercio.sdk.crypto.KeysHelper
import network.commercio.sdk.entities.docs.CommercioDoc
import network.commercio.sdk.entities.docs.CommercioDocReceipt
import network.commercio.sdk.entities.docs.MsgSendDocumentReceipt
import network.commercio.sdk.entities.docs.MsgShareDocument
import network.commercio.sdk.entities.id.Did
import network.commercio.sdk.networking.Network
import network.commercio.sdk.tx.TxHelper
import network.commercio.sdk.tx.TxHelper.BroadcastingMode
import java.util.*
import javax.crypto.SecretKey

/**
 * Allows to easily perform CommercioDOCS related operations
 */
object DocsHelper {

    init {
        System.setProperty("javax.net.ssl.trustStoreType", "JKS")
    }

    /**
     * Creates a new transaction that allows to share the document associated with the given [contentUri] and
     * having the given [metadata] and [checksum]. If [encryptedData] is specified, encrypts the proper data for
     * the specified [recipients] and then sends the transaction to the blockchain.
     */
    @JvmOverloads
    suspend fun shareDocument(
        id: String,
        metadata: CommercioDoc.Metadata,
        recipients: List<Did>,
        wallet: Wallet,
        doSign: CommercioDoc.CommercioDoSign? = null,
        checksum: CommercioDoc.Checksum? = null,
        aesKey: SecretKey = KeysHelper.generateAesKey(),
        encryptedData: List<EncryptedData> = listOf(),
        fee: StdFee? = null,
        contentUri: String = "",
        mode: BroadcastingMode? = null
    ): TxResponse {

        // Build CommercioDoc
        val commercioDoc = CommercioDocHelper.fromWallet(
            id = id,
            metadata = metadata,
            recipients = recipients,
            wallet = wallet,
            doSign = doSign,
            checksum = checksum,
            aesKey = aesKey,
            encryptedData = encryptedData,
            contentUri = contentUri
        )

        // Build the tx message
        val msg = MsgShareDocument(document = commercioDoc)
        val result = TxHelper.createSignAndSendTx(
            msgs = listOf(msg),
            fee = fee,
            wallet = wallet,
            mode = mode
        )
        return result
    }

    /**
     * Creates a new transaction that allows to share the list of CommercioDoc in the given [commercioDocs]
     */
    @JvmOverloads
    suspend fun shareDocumentsList(
        commercioDocs: List<CommercioDoc>,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {

        // Build the tx message
        val msgs = commercioDocs.map {
            MsgShareDocument(document = it)
        }

        val result = TxHelper.createSignAndSendTx(
            msgs = msgs,
            fee = fee,
            wallet = wallet,
            mode = mode
        )
        return result
    }

    /**
     * Returns the list of all the [CommercioDoc] that the specified [address] has sent.
     */
    suspend fun getSentDocuments(address: Did, wallet: Wallet): List<CommercioDoc> {
        val queryUrl = "${wallet.networkInfo.lcdUrl}/docs/${address.value}/sent"
        val docsToConvert = Network.queryChain<List<Any>>(queryUrl) ?: listOf()
        return docsToConvert.map { jacksonObjectMapper().convertValue(it, CommercioDoc::class.java) }
    }

    /**
     * Returns the list of all the [CommercioDoc] that the specified [address] has received.
     */
    suspend fun getReceivedDocuments(address: Did, wallet: Wallet): List<CommercioDoc> {
        val queryUrl = "${wallet.networkInfo.lcdUrl}/docs/${address.value}/received"
        val docsToConvert = Network.queryChain<List<Any>>(queryUrl) ?: listOf()
        return docsToConvert.map { jacksonObjectMapper().convertValue(it, CommercioDoc::class.java) }
    }

    /**
     * Creates a new transaction which tells the [recipient] that the document having the specified [documentId] and
     * present inside the transaction with hash [txHash] has been properly seen.
     * @param proof optional proof of reading.
     */
    @JvmOverloads
    suspend fun sendDocumentReceipt(
        recipient: Did,
        txHash: String,
        documentId: String,
        proof: String = "",
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {
        val msg = MsgSendDocumentReceipt(
            CommercioDocReceipt(
                uuid = UUID.randomUUID().toString(),
                recipientDid = recipient.value,
                txHash = txHash,
                documentUuid = documentId,
                proof = proof,
                senderDid = wallet.bech32Address
            )
        )
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet, fee = fee, mode = mode)
    }

    /**
     * Returns the list of all the [CommercioDocReceipt] that have been sent from the given [address].
     */
    suspend fun getSentReceipts(address: Did, wallet: Wallet): List<CommercioDocReceipt> {
        val queryUrl = "${wallet.networkInfo.lcdUrl}/receipts/${address.value}/sent"
        val receiptsToConvert = Network.queryChain<List<Any>>(queryUrl) ?: listOf()
        return receiptsToConvert.map { jacksonObjectMapper().convertValue(it, CommercioDocReceipt::class.java) }
    }

    /**
     * Returns the list of all the [CommercioDocReceipt] that have been received from the given [address].
     */
    suspend fun getReceivedReceipts(address: Did, wallet: Wallet): List<CommercioDocReceipt> {
        val queryUrl = "${wallet.networkInfo.lcdUrl}/receipts/${address.value}/received"
        val receiptsToConvert = Network.queryChain<List<Any>>(queryUrl) ?: listOf()
        return receiptsToConvert.map { jacksonObjectMapper().convertValue(it, CommercioDocReceipt::class.java) }
    }
}
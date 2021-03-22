package network.commercio.sdk.docs

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.TxResponse
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdFee
import network.commercio.sacco.models.types.StdMsg
import network.commercio.sdk.crypto.KeysHelper
import network.commercio.sdk.entities.docs.CommercioDoc
import network.commercio.sdk.entities.docs.CommercioDocReceipt
import network.commercio.sdk.entities.docs.EncryptedData
import network.commercio.sdk.entities.docs.MsgSendDocumentReceipt
import network.commercio.sdk.entities.docs.MsgShareDocument
import network.commercio.sdk.entities.id.Did
import network.commercio.sdk.networking.Network
import network.commercio.sdk.networking.isVersion
import network.commercio.sdk.tx.TxHelper
import network.commercio.sdk.tx.TxHelper.BroadcastingMode
import javax.crypto.SecretKey
import network.commercio.sdk.entities.docs.legacy.`21`.CommercioDocMapper as CommercioDocMapperLegacy
import network.commercio.sdk.entities.docs.legacy.`21`.CommercioDoc as CommercioDocLegacy
import network.commercio.sdk.entities.docs.legacy.`21`.MsgShareDocument as MsgShareDocumentLegacy
import network.commercio.sdk.entities.docs.legacy.`21`.CommercioDocReceiptMapper as CommercioDocReceiptMapperLegacy
import network.commercio.sdk.entities.docs.legacy.`21`.MsgSendDocumentReceipt as MsgSendDocumentReceiptLegacy


/**
 * Allows to easily perform CommercioDOCS related operations
 */
object DocsHelper {

    init {
        System.setProperty("javax.net.ssl.trustStoreType", "JKS")
    }

    /**
     * Creates a new transaction that allows to share the document associated
     * with the given [metadata] and having the optional [contentUri], [doSign],
     * [checksum], [fee] and broadcasting [mode].
     *
     * If [encryptedData] is specified then encrypts the proper data for
     * the specified [recipients] and then sends the transaction to the blockchain.
     *
     * If [doSign] is specified then the field [checksum] must be also provided.
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
        var msg: StdMsg = MsgShareDocument(document = commercioDoc)

        val isLegacy21Chain = wallet.networkInfo.isVersion(version = "2.1")
        if (isLegacy21Chain) {
            // Convert the new CommercioDoc entity to the old format
            val legacy21Doc: CommercioDocLegacy = CommercioDocMapperLegacy().toLegacy(commercioDoc)

            // Replace the msg with the newer document with the legacy one
            msg = MsgShareDocumentLegacy(document = legacy21Doc)
        }

        return TxHelper.createSignAndSendTx(msgs = listOf(msg), fee = fee, wallet = wallet, mode = mode)
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
        var msgs: List<StdMsg> = mutableListOf()

        // Build the tx message
        val isLegacy21Chain = wallet.networkInfo.isVersion(version = "2.1")
        if (isLegacy21Chain) {

            msgs = commercioDocs.map {
                // Convert the new CommercioDoc entity to the old format
                MsgShareDocumentLegacy(document = CommercioDocMapperLegacy().toLegacy(it))
            }.toList()

        } else {
            msgs = commercioDocs.map { MsgShareDocument(document = it) }.toList()
        }
        return TxHelper.createSignAndSendTx(msgs = msgs, fee = fee, wallet = wallet, mode = mode)
    }

    /**
     * Returns the list of all the [CommercioDoc] that the specified [address] has sent.
     */
    suspend fun getSentDocuments(address: String, networkInfo: NetworkInfo): List<CommercioDoc> {
        val queryUrl = "${networkInfo.lcdUrl}/docs/$address/sent"
        val docsToConvert = Network.queryChain<List<Any>>(queryUrl) ?: listOf()
        return docsToConvert.map { jacksonObjectMapper().convertValue(it, CommercioDoc::class.java) }
    }

    /**
     * Returns the list of all the [CommercioDoc] that the specified [address] has received.
     */
    suspend fun getReceivedDocuments(address: String, networkInfo: NetworkInfo): List<CommercioDoc> {
        val queryUrl = "${networkInfo.lcdUrl}/docs/$address/received"
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

        val commercioDocReceipt = CommercioDocReceiptHelper.fromWallet(
            wallet = wallet,
            recipient = recipient,
            txHash = txHash,
            documentId = documentId,
            proof = proof
        )

        var msg: StdMsg = MsgSendDocumentReceipt(commercioDocReceipt)

        val isLegacy21Chain = wallet.networkInfo.isVersion(version = "2.1")
        if (isLegacy21Chain) {
            // Convert the new CommercioDocReceipt entity to the old format
            val legacy21Receipt = CommercioDocReceiptMapperLegacy().toLegacy(commercioDocReceipt)

            // Replace the msg with the newer document with the legacy one
            msg = MsgSendDocumentReceiptLegacy(receipt = legacy21Receipt)
        }
        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet, fee = fee, mode = mode)
    }

    /**
     * Creates a new transaction with the list of CommercioDocReceipt given
     */
    @JvmOverloads
    suspend fun sendDocumentReceiptsList(
        commercioDocReceipts: List<CommercioDocReceipt>,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse {
        var msgs: List<StdMsg> = mutableListOf()

        // Build the tx message
        val isLegacy21Chain = wallet.networkInfo.isVersion(version = "2.1")
        if (isLegacy21Chain) {

            msgs = commercioDocReceipts.map {
                // Convert the new CommercioDocReceipt entity to the old format
                MsgSendDocumentReceiptLegacy(receipt = CommercioDocReceiptMapperLegacy().toLegacy(it))
            }.toList()

        } else {
            msgs = commercioDocReceipts.map { MsgSendDocumentReceipt(receipt = it) }.toList()
        }
        return TxHelper.createSignAndSendTx(msgs = msgs, wallet = wallet, fee = fee, mode = mode)
    }


    /**
     * Returns the list of all the [CommercioDocReceipt] that have been sent from the given [address].
     */
    suspend fun getSentReceipts(address: String, networkInfo: NetworkInfo): List<CommercioDocReceipt> {
        val queryUrl = "${networkInfo.lcdUrl}/receipts/$address/sent"
        val receiptsToConvert = Network.queryChain<List<Any>>(queryUrl) ?: listOf()
        return receiptsToConvert.map { jacksonObjectMapper().convertValue(it, CommercioDocReceipt::class.java) }
    }

    /**
     * Returns the list of all the [CommercioDocReceipt] that have been received from the given [address].
     */
    suspend fun getReceivedReceipts(address: String, networkInfo: NetworkInfo): List<CommercioDocReceipt> {
        val queryUrl = "${networkInfo.lcdUrl}/receipts/$address/received"
        val receiptsToConvert = Network.queryChain<List<Any>>(queryUrl) ?: listOf()
        return receiptsToConvert.map { jacksonObjectMapper().convertValue(it, CommercioDocReceipt::class.java) }
    }
}
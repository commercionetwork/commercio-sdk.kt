package network.commercio.sdk.docs

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
import java.util.*
import javax.crypto.SecretKey

/**
 * Allows to easily perform CommercioDOCS related operations
 */
object DocsHelper {

    /**
     * Creates a new transaction that allows to share the document associated with the given [contentUri] and
     * having the given [metadata] and [checksum]. If [encryptedData] is specified, encrypts the proper data for
     * the specified [recipients] and then sends the transaction to the blockchain.
     */
    @JvmOverloads
    suspend fun shareDocument(
        id: String,
        contentUri: String ="",
        metadata: CommercioDoc.Metadata,
        recipients: List<Did>,
        wallet: Wallet,
        checksum: CommercioDoc.Checksum? = null,
        aesKey: SecretKey = KeysHelper.generateAesKey(),
        encryptedData: List<EncryptedData> = listOf(),
        doSign: CommercioDoc.CommercioDoSign? = null,
        fee: StdFee? = null
    ): TxResponse {

        // Build a generic document
        val document = CommercioDoc(
            senderDid = wallet.bech32Address,
            recipientsDids = recipients.map { it.value },
            uuid = id,
            contentUri = contentUri,
            metadata = metadata,
            checksum = checksum,
            encryptionData = null,
            doSign = doSign
        )

        // Encrypt its contents, if necessary
        val finalDoc = when (encryptedData.isEmpty()) {
            true -> document
            false -> document.encryptField(aesKey, encryptedData, recipients, wallet)
        }


        // Build the tx message
        val msg = MsgShareDocument(document = finalDoc)

        val result= TxHelper.createSignAndSendTx(
            msgs = listOf(msg),
            fee = fee,
            wallet = wallet
        )

        return result
    }

    /**
     * Returns the list of all the [CommercioDoc] that the specified [address] has sent.
     */
    suspend fun getSentDocuments(address: Did, wallet: Wallet): List<CommercioDoc> {
        val queryUrl = "${wallet.networkInfo.lcdUrl}/docs/${address.value}/sent"
        return Network.queryChain<List<CommercioDoc>>(queryUrl) ?: listOf()
    }

    /**
     * Returns the list of all the [CommercioDoc] that the specified [address] has received.
     */
    suspend fun getReceivedDocuments(address: Did, wallet: Wallet): List<CommercioDoc> {
        val queryUrl = "${wallet.networkInfo.lcdUrl}/docs/${address.value}/received"
        return Network.queryChain<List<CommercioDoc>>(queryUrl) ?: listOf()
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
        fee: StdFee? = null
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

        return TxHelper.createSignAndSendTx(msgs = listOf(msg), wallet = wallet, fee = fee)
    }

    /**
     * Returns the list of all the [CommercioDocReceipt] that have been sent from the given [address].
     */
    suspend fun getSentReceipts(address: Did, wallet: Wallet): List<CommercioDocReceipt> {
        val queryUrl = "${wallet.networkInfo.lcdUrl}/receipts/${address.value}/sent"
        return Network.queryChain<List<CommercioDocReceipt>>(queryUrl) ?: listOf()
    }

    /**
     * Returns the list of all the [CommercioDocReceipt] that have been received from the given [address].
     */
    suspend fun getReceivedReceipts(address: Did, wallet: Wallet): List<CommercioDocReceipt> {
        val queryUrl = "${wallet.networkInfo.lcdUrl}/receipts/${address.value}/received"
        return Network.queryChain<List<CommercioDocReceipt>>(queryUrl) ?: listOf()
    }
}
package network.commercio.sdk.docs

import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sacco.models.types.StdFee
import network.commercio.sdk.crypto.TxHelper
import network.commercio.sdk.entities.Did
import network.commercio.sdk.entities.docs.CommercioDoc
import network.commercio.sdk.entities.docs.MsgShareDocument
import java.util.*

/**
 * Allows to easily perform CommercioDOCS related operations
 */
object DocsHelper {


    /**
     * Creates a MsgShareDocument transaction and sends it through.
     */
    @JvmOverloads
    suspend fun shareDocument(
        contentUri: String,
        metadata: CommercioDoc.Metadata,
        checksum: CommercioDoc.Checksum,
        recipients: List<Did>,
        wallet: Wallet,
        encryptedData: List<EncryptedData> = listOf()
    ): String {

        // Build a generic document
        val document = CommercioDoc(
            uuid = UUID.randomUUID().toString(),
            contentUri = contentUri,
            metadata = metadata,
            checksum = checksum,
            encryptionData = null
        )

        // Encrypt its contents, if necessary
        val finalDoc = when (encryptedData.isEmpty()) {
            true -> document
            false -> document.encryptField(encryptedData, recipients, wallet)
        }

        // Build the tx message
        val msg = MsgShareDocument(
            senderDid = wallet.bech32Address,
            document = finalDoc,
            recipientsDid = recipients.map { it.value }
        )

        return TxHelper.createSignAndSendTx(
            msgs = listOf(msg),
            fee = StdFee(
                gas = "200000",
                amount = listOf(StdCoin(denom = "uccc", amount = "10000"))
            ),
            wallet = wallet
        )
    }
}
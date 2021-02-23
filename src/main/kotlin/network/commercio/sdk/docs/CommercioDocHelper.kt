package network.commercio.sdk.docs

import network.commercio.sacco.Wallet
import network.commercio.sdk.crypto.KeysHelper
import network.commercio.sdk.entities.docs.CommercioDoc
import network.commercio.sdk.entities.docs.EncryptedData
import network.commercio.sdk.entities.id.Did
import javax.crypto.SecretKey

/**
 * Allows to easily create a CommercioDoc
 * and perform common related operations
 */
object CommercioDocHelper {

    /**
     * Creates a new CommercioDoc that allows to share the document associated with the given [contentUri] and
     * having the given [metadata] and [checksum].
     *
     * If [encryptedData] is specified, encrypts the proper data for
     * the specified [recipients] and then sends the transaction to the blockchain.
     */
    suspend fun fromWallet(
        id: String,
        metadata: CommercioDoc.Metadata,
        recipients: List<Did>,
        wallet: Wallet,
        doSign: CommercioDoc.CommercioDoSign? = null,
        checksum: CommercioDoc.Checksum? = null,
        aesKey: SecretKey = KeysHelper.generateAesKey(),
        encryptedData: List<EncryptedData> = listOf(),
        contentUri: String = ""
    ): CommercioDoc {
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
        return when (encryptedData.isEmpty()) {
            true -> document
            false -> document.encryptField(aesKey, encryptedData, recipients, wallet)
        }
    }
}

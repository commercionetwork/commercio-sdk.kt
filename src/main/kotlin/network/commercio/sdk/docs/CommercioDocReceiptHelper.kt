package network.commercio.sdk.docs

import network.commercio.sacco.Wallet
import network.commercio.sdk.entities.docs.CommercioDocReceipt
import network.commercio.sdk.entities.id.Did
import java.util.*


/**
 * Allows to easily create a [CommercioDocReceipt]
 * and perform common related operations.
 */
object CommercioDocReceiptHelper {

    /**
     * Build a `CommercioDocReceipt` from the given [wallet], [recipient],
     * [txHash], [documentId] and an optional [proof].
     *
     * The [txHash] is the transaction in the blockchain that contains the
     * [CommercioDoc] that you want to generate a recepit for. You can get the
     * [recipient], that is, the [CommercioDoc] sender and the [documentId] from
     * the transaction data.
     *
     * The [proof] should be some kind of agreeded method between the
     * [CommercioDoc] sender and receivers that the document has been read.
     */
    fun fromWallet(
        recipient: Did,
        txHash: String,
        documentId: String,
        proof: String,
        wallet: Wallet
    ): CommercioDocReceipt {
        // Build CommercioDocReceipt
        return CommercioDocReceipt(
            uuid = UUID.randomUUID().toString(),
            recipientDid = recipient.value,
            txHash = txHash,
            documentUuid = documentId,
            proof = proof,
            senderDid = wallet.bech32Address
        )
    }
}

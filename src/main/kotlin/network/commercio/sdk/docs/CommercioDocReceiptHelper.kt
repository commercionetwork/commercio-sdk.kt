package network.commercio.sdk.docs

import network.commercio.sacco.Wallet
import network.commercio.sdk.entities.docs.CommercioDocReceipt
import network.commercio.sdk.entities.id.Did
import java.util.*

/**
 * Allows to easily build CommercioDocReceipt
 */
object CommercioDocReceiptHelper {

    /**
     * Creates a new CommercioDocReceipt that allows to share the document associated with the given [wallet] and
     * having the given [txHash], [documentId] and [recipient].
     */
    fun fromWallet(
        recipient: Did,
        txHash: String,
        documentId: String,
        proof: String = "",
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

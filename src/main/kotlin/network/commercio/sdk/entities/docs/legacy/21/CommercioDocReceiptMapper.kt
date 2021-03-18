package network.commercio.sdk.entities.docs.legacy.`21`

import network.commercio.sdk.entities.docs.CommercioDocReceipt
import network.commercio.sdk.entities.docs.legacy.`21`.CommercioDocReceipt as CommercioDocReceiptLegacy

class CommercioDocReceiptMapper {
    fun toLegacy(docReceipt: CommercioDocReceipt): CommercioDocReceiptLegacy {
        return CommercioDocReceiptLegacy(
            documentUuid = docReceipt.documentUuid,
            recipientDid = docReceipt.recipientDid,
            senderDid = docReceipt.senderDid,
            txHash = docReceipt.txHash,
            uuid = docReceipt.uuid,
            proof = docReceipt.proof
        )
    }
}
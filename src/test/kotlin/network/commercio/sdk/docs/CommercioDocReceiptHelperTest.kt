package network.commercio.sdk

import kotlinx.coroutines.runBlocking
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.Wallet
import network.commercio.sdk.docs.CommercioDocReceiptHelper
import network.commercio.sdk.entities.docs.CommercioDocReceipt
import network.commercio.sdk.entities.id.Did
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.util.*


@Suppress("EXPERIMENTAL_API_USAGE")
class CommercioDocReceiptHelperTest {

    private val info = NetworkInfo(bech32Hrp = "did=com=", lcdUrl = "")

    val mnemonicString =
        "dash ordinary anxiety zone slot rail flavor tortoise guilt divert pet sound ostrich increase resist short ship lift town ice split payment round apology"
    val mnemonic = mnemonicString.split(" ")
    val wallet = Wallet.derive(mnemonic, info)


    @Test
    fun `fromWallet builds CommercioDocReceipt correctly`() = runBlocking {

        val uuid = UUID.randomUUID().toString()
        val txHash = "txHash"
        val documentId = UUID.randomUUID().toString()

        val expectedDocReceipt = CommercioDocReceipt(
            senderDid = wallet.bech32Address,
            uuid = uuid,
            recipientDid = wallet.bech32Address,
            txHash = txHash,
            documentUuid = documentId
        )

        val commercioDocReceipt = CommercioDocReceiptHelper.fromWallet(
            wallet = wallet,
            recipient = Did(wallet.bech32Address),
            txHash = txHash,
            documentId = documentId
        )

        assertNotEquals(commercioDocReceipt.uuid, expectedDocReceipt.uuid)
        assertEquals(commercioDocReceipt.senderDid, expectedDocReceipt.senderDid)
        assertEquals(commercioDocReceipt.recipientDid, expectedDocReceipt.recipientDid)
        assertEquals(commercioDocReceipt.txHash, expectedDocReceipt.txHash)
        assertEquals(commercioDocReceipt.documentUuid, expectedDocReceipt.documentUuid)
        assertEquals(commercioDocReceipt.proof, "")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `fromWallet for CommercioDocReceipt should throw an IllegalArgumentException because DOCUMENT_UUID requires a valid UUID v4 format`() {

        CommercioDocReceiptHelper.fromWallet(
            wallet = wallet,
            recipient = Did(wallet.bech32Address),
            txHash = "",
            documentId = "doc invalid uuid"
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `fromWallet for CommercioDocReceipt should throw an IllegalArgumentException because RECIPIENT requires a valid Bech32 format`() {

        CommercioDocReceiptHelper.fromWallet(
            wallet = wallet,
            recipient = Did("doc invalid uuid"),
            txHash = "",
            documentId = UUID.randomUUID().toString()
        )
    }
}
package network.commercio.sdk

import kotlinx.coroutines.runBlocking
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.TxResponse
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sdk.crypto.KeysHelper
import network.commercio.sdk.docs.DocsHelper
import network.commercio.sdk.entities.docs.CommercioDoc
import network.commercio.sdk.entities.id.Did
import network.commercio.sdk.id.DidDocumentHelper
import network.commercio.sdk.id.IdHelper
import org.junit.Assert.assertTrue
import org.junit.Test
import java.security.KeyPair
import java.util.*

class Examples {

    private val info = NetworkInfo(bech32Hrp = "did:com:", lcdUrl = "http://localhost:1317")

    private val userMnemonic = listOf(
        "will",
        "hard",
        "topic",
        "spray",
        "beyond",
        "ostrich",
        "moral",
        "morning",
        "gas",
        "loyal",
        "couch",
        "horn",
        "boss",
        "across",
        "age",
        "post",
        "october",
        "blur",
        "piece",
        "wheel",
        "film",
        "notable",
        "word",
        "man"
    )
    private val userWallet = Wallet.derive(mnemonic = userMnemonic, networkInfo = info)

    @Test
    fun `IdHelper examples`() = runBlocking {

        // --- Set the Did Document
        val rsaKeyPair = KeysHelper.generateRsaKeyPair()
        val ecKeyPair = KeysHelper.generateEcKeyPair()
        // createDidDocument(wallet = userWallet, rsaKeyPair = rsaKeyPair, ecKeyPair = ecKeyPair)

        // --- Request the Did deposit
        val depositAmount = listOf(StdCoin(denom = "ucommercio", amount = "10"))
        // postDepositRequest(amount = depositAmount, wallet = userWallet)

        // --- Request the Did power up
        val pairwiseMnemonic = listOf(
            "push",
            "grace",
            "power",
            "desk",
            "arrive",
            "horror",
            "gallery",
            "physical",
            "kingdom",
            "ecology",
            "fat",
            "firm",
            "future",
            "service",
            "table",
            "little",
            "live",
            "reason",
            "maximum",
            "short",
            "motion",
            "planet",
            "stage",
            "second"
        )
        val pairwiseWallet = Wallet.derive(mnemonic = pairwiseMnemonic, networkInfo = info)
        // postPowerUpRequest(pairwiseDid = pairwiseWallet.bech32Address, amount = depositAmount, wallet = userWallet)

    }

    /**
     * Shows how to create a Did Document and associate it to an existing account Did.
     * Documentation: https://docs.commercio.network/x/id/tx/associate-a-did-document.html
     */
    private suspend fun createDidDocument(wallet: Wallet, rsaKeyPair: KeyPair, ecKeyPair: KeyPair) {
        val didDocument = DidDocumentHelper.fromWallet(wallet, listOf(rsaKeyPair.public, ecKeyPair.public))
        val response = IdHelper.setDidDocument(didDocument, wallet)
        assertTrue(response is TxResponse.Successful)
    }

    /**
     * Shows how to post a request for a deposit that will be later read from the centralized APIs.
     * Documentation: https://docs.commercio.network/x/id/tx/request-did-deposit.html
     */
    private suspend fun postDepositRequest(amount: List<StdCoin>, wallet: Wallet) {
        val response = IdHelper.requestDidDeposit(
            recipient = Did(wallet.bech32Address),
            amount = amount,
            wallet = wallet
        )
        assertTrue(response is TxResponse.Successful)
    }

    /**
     * Shows how to request a pairwise Did power up. This request will later be read and handled by the centralized
     * APIs that will send the funds to such account.
     * Documentation: https://docs.commercio.network/x/id/tx/request-did-power-up.html
     */
    private suspend fun postPowerUpRequest(pairwiseDid: String, amount: List<StdCoin>, wallet: Wallet) {
        val response = IdHelper.requestDidPowerUp(
            pairwiseDid = Did(pairwiseDid),
            amount = amount,
            wallet = wallet
        )
        assertTrue(response is TxResponse.Successful)
    }

    @Test
    fun `DocsHelper examples`() = runBlocking {
        val recipientMnemonic = listOf(
            "crisp",
            "become",
            "thumb",
            "fetch",
            "forest",
            "senior",
            "polar",
            "slush",
            "wise",
            "wash",
            "doctor",
            "sunset",
            "skate",
            "disease",
            "power",
            "tool",
            "sock",
            "upper",
            "diary",
            "what",
            "trap",
            "artist",
            "wood",
            "cereal"
        )
        val recipientWallet = Wallet.derive(recipientMnemonic, info)

        // --- Share a document
        val docRecipientDid = Did(recipientWallet.bech32Address)
        // val (docId, txHash) = shareDocument(recipients = listOf(docRecipientDid), wallet = userWallet)

        // --- Share receipt
        val receiptRecipientDid = Did(userWallet.bech32Address)
        // sendDocumentReceipt(docId = docId, txHash = txHash, recipient = receiptRecipientDid, wallet = recipientWallet)
    }

    /**
     * Shows how to share a document to the given recipients.
     * Documentation: https://docs.commercio.network/x/docs/tx/send-document.html
     */
    private suspend fun shareDocument(recipients: List<Did>, wallet: Wallet): Pair<String, String> {
        val docId = UUID.randomUUID().toString()
        val response = DocsHelper.shareDocument(
            id = docId,
            contentUri = "https://example.com/document",
            metadata = CommercioDoc.Metadata(
                contentUri = "https://example.com/document/metadata",
                schema = CommercioDoc.Metadata.Schema(
                    uri = "https://example.com/custom/metadata/schema",
                    version = "1.0.0"
                )
            ),
            recipients = recipients,
            fees = listOf(StdCoin(denom = "ucommercio", amount = "10000")),
            wallet = wallet
        )
        assertTrue(response is TxResponse.Successful)
        return docId to (response as TxResponse.Successful).txHash
    }

    /**
     * Shows how to send a document receipt to the specified [recipient] for the given [docId] present
     * inside the transaction having the given [txHash].
     */
    private suspend fun sendDocumentReceipt(docId: String, txHash: String, recipient: Did, wallet: Wallet) {
        val response = DocsHelper.sendDocumentReceipt(
            recipient = recipient,
            txHash = txHash,
            documentId = docId,
            wallet = wallet
        )
        assertTrue(response is TxResponse.Successful)
    }


}
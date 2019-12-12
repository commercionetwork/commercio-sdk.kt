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
import network.commercio.sdk.entities.membership.MembershipType
import network.commercio.sdk.crypto.CertificateHelper
import network.commercio.sdk.id.DidDocumentHelper
import network.commercio.sdk.id.IdHelper
import network.commercio.sdk.membership.MembershipHelper
import network.commercio.sdk.mint.MintHelper
import org.junit.Assert.assertTrue
import org.junit.Test
import java.security.KeyPair
import java.util.*

/**
 * Contains a list of methods that illustrate how to use each and every method that is present inside the
 * different helpers contained into this SDK.
 */
@Suppress("EXPERIMENTAL_API_USAGE")
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

    @Test
    fun `CertificateHelper examples`() = runBlocking {
        val rsaKeyPair = KeysHelper.generateRsaKeyPair()
        val exportedPublic = KeysHelper.exportPublicKeyHEX(rsaKeyPair.public, "RSA")
        val exportedPrivate = KeysHelper.exportPrivateKeyHEX(rsaKeyPair.private, "RSA")
        val cert = createX509Certificate(userWallet, rsaKeyPair)
        println(cert)
        println("Public: $exportedPublic")
        println("Private: $exportedPrivate")
    }

    private fun createX509Certificate(wallet: Wallet, rsaKeyPair: KeyPair): String {
        val certificate = CertificateHelper.x509certificateFromWallet(wallet.bech32Address, rsaKeyPair)
        return CertificateHelper.getPem(certificate)
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

    @Test
    fun `MintHelper examples`() = runBlocking {

        // --- Open CDP
        // openCdp(amount = 100_000.toUInt(), wallet = userWallet)

        // --- Close CDP
        // closeCdp(timestamp = 4, wallet = userWallet)
    }

    /**
     * Shows how to open a new Collateralized Debt Position in order to get half the specified [amount] of
     * Commercio Cash Credits millionth parts (`uccc`).
     * Please note that `uccc` are millionth of Commercio Cash Credits and thus to send one document you wil need
     * 10.000 `uccc`.
     */
    private suspend fun openCdp(amount: UInt, wallet: Wallet) {
        val response = MintHelper.openCdp(commercioTokenAmount = amount, wallet = wallet)
        assertTrue(response is TxResponse.Successful)
    }

    /**
     * Shows how to close a Collateralized Debt Position in order to allow the user controlling the given [wallet]
     * to get back the amount of pico Commercio Tokens (`ucommercio`) giving back the lent pico Commercio
     * Cash Credits (`uccc`).
     */
    private suspend fun closeCdp(timestamp: Int, wallet: Wallet) {
        val response = MintHelper.closeCdp(timestamp = timestamp, wallet = wallet)
        assertTrue(response is TxResponse.Successful)
    }

    @Test
    fun `MembershipsHelper examples`() = runBlocking {
        val newUserMnemonic = listOf(
            "often",
            "emerge",
            "table",
            "boat",
            "add",
            "crowd",
            "obtain",
            "creek",
            "skill",
            "flat",
            "master",
            "gift",
            "provide",
            "peasant",
            "famous",
            "blur",
            "flight",
            "lady",
            "elephant",
            "twenty",
            "join",
            "depth",
            "laptop",
            "arrest"
        )
        val newUserWallet = Wallet.derive(newUserMnemonic, info)

        // --- Invite user
        // inviteUser(user = Did(newUserWallet.bech32Address), wallet = userWallet)

        // --- Buy a membership
        // buyMembership(membershipType = MembershipType.GOLD, wallet = newUserWallet)
    }

    /**
     * Shows how to perform a transaction to invite a user.
     * Note that in order to invite a user, you must already have a membership.
     */
    private suspend fun inviteUser(user: Did, wallet: Wallet) {
        val response = MembershipHelper.inviteUser(user, wallet)
        assertTrue(response is TxResponse.Successful)
    }

    /**
     * Shows how to perform the transaction that allows the owner of the given [wallet] to buy a membership
     * of the specified [membershipType].
     */
    private suspend fun buyMembership(membershipType: MembershipType, wallet: Wallet) {
        val response = MembershipHelper.buyMembership(membershipType, wallet)
        assertTrue(response is TxResponse.Successful)
    }
}

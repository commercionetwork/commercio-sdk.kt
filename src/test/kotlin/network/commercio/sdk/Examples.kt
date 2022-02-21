package network.commercio.sdk

import KeyPairWrapper
import kotlinx.coroutines.runBlocking
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.TxResponse
import network.commercio.sacco.Wallet
import network.commercio.sacco.models.messages.MsgSend
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sacco.models.types.StdFee
import network.commercio.sdk.crypto.CertificateHelper
import network.commercio.sdk.crypto.KeysHelper
import network.commercio.sdk.docs.DocsHelper
import network.commercio.sdk.entities.docs.CommercioDoc
import network.commercio.sdk.entities.id.Did
import network.commercio.sdk.entities.kyc.BuyMembership
import network.commercio.sdk.entities.kyc.InviteUser
import network.commercio.sdk.entities.kyc.MembershipType
import network.commercio.sdk.entities.kyc.RewardPoolDeposit
import network.commercio.sdk.entities.mint.BurnCcc
import network.commercio.sdk.entities.mint.MintCcc
import network.commercio.sdk.id.DidDocumentHelper
import network.commercio.sdk.id.IdHelper
import network.commercio.sdk.kyc.InviteUserHelper
import network.commercio.sdk.kyc.KycHelper
import network.commercio.sdk.kyc.RewardPoolDepositHelper
import network.commercio.sdk.mint.MintHelper
import network.commercio.sdk.tx.TxHelper
import org.junit.Assert.assertTrue
import org.junit.Test
import java.security.KeyPair
import java.security.interfaces.RSAPrivateKey
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

        // --- Optional
        val fee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "10000")))
        val mode = TxHelper.BroadcastingMode.BROADCAST_MODE_BLOCK

        // --- Set the Did Document
        val rsaVerificationKeyPair = KeysHelper.generateRsaKeyPair()
        val rsaSignatureKeyPair = KeysHelper.generateRsaKeyPair(type = "RsaSignatureKey2018")
        //createDidDocument(wallet = userWallet, rsaVerificationKeyPair= rsaVerificationKeyPair ,rsaSignatureKeyPair= rsaSignatureKeyPair, fee = fee, mode = mode)

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
        //postPowerUpRequest(pairwiseDid = pairwiseWallet.bech32Address, amount = depositAmount, wallet = userWallet, privateKey = privateKey, fee = fee, mode = mode)
    }

    @Test
    fun `CertificateHelper examples`() = runBlocking {
        val rsaKeyPair = KeysHelper.generateRsaKeyPair()
        val exportedPublic = KeysHelper.exportPublicKeyHEX(rsaKeyPair.publicWrapper.public, "RSA")
        val exportedPrivate = KeysHelper.exportPrivateKeyHEX(rsaKeyPair.private, "RSA")
        val cert = createX509Certificate(userWallet, rsaKeyPair.toKeyPair())
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
    private suspend fun createDidDocument(
        wallet: Wallet,
        rsaVerificationKeyPair: KeyPairWrapper,
        rsaSignatureKeyPair: KeyPairWrapper,
        fee: StdFee,
        mode: TxHelper.BroadcastingMode
    ) {
        val didDocument = DidDocumentHelper.fromWallet(
            wallet,
            listOf(rsaVerificationKeyPair.publicWrapper, rsaSignatureKeyPair.publicWrapper)
        )
        val response = IdHelper.setDidDocument(
            didDocument = didDocument,
            wallet = wallet,
            fee = fee,
            mode = mode
        )
        assertTrue(response is TxResponse.Successful)
    }

    /**
     * Shows how to request a pairwise Did power up. This request will later be read and handled by the centralized
     * APIs that will send the funds to such account.
     * Documentation: https://docs.commercio.network/x/id/tx/request-did-power-up.html
     */
    private suspend fun postPowerUpRequest(
        pairwiseDid: String,
        amount: List<StdCoin>,
        wallet: Wallet,
        privateKey: RSAPrivateKey,
        fee: StdFee,
        mode: TxHelper.BroadcastingMode
    ) {
        val response = IdHelper.requestDidPowerUp(
            pairwiseDid = Did(pairwiseDid),
            amount = amount,
            wallet = wallet,
            privateKey = privateKey,
            fee = fee,
            mode = mode
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

        // --- Optional
        val fee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "10000")))
        val mode = TxHelper.BroadcastingMode.BROADCAST_MODE_BLOCK

        // --- Share a document
        val docRecipientDid = Did(recipientWallet.bech32Address)
        // val (docId, txHash) = shareDocument(recipients = listOf(docRecipientDid), wallet = userWallet, fee = fee, mode = mode)

        // --- Share receipt
        val receiptRecipientDid = Did(userWallet.bech32Address)
        // sendDocumentReceipt(docId = docId, txHash = txHash, recipient = receiptRecipientDid, wallet = recipientWallet, fee = fee, mode = mode)
    }

    /**
     * Shows how to share a document to the given recipients.
     * Documentation: https://docs.commercio.network/x/docs/tx/send-document.html
     */
    private suspend fun shareDocument(
        recipients: List<Did>,
        wallet: Wallet,
        fee: StdFee,
        mode: TxHelper.BroadcastingMode
    ): Pair<String, String> {
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
            wallet = wallet,
            fee = fee,
            mode = mode
        )
        assertTrue(response is TxResponse.Successful)
        return docId to (response as TxResponse.Successful).txHash
    }


    /**
     * Shows how to send a document receipt to the specified [recipient] for the given [docId] present
     * inside the transaction having the given [txHash].
     */
    private suspend fun sendDocumentReceipt(
        docId: String,
        txHash: String,
        recipient: Did,
        wallet: Wallet,
        fee: StdFee,
        mode: TxHelper.BroadcastingMode
    ) {
        val response = DocsHelper.sendDocumentReceipt(
            recipient = recipient,
            txHash = txHash,
            documentId = docId,
            wallet = wallet,
            fee = fee,
            mode = mode
        )
        assertTrue(response is TxResponse.Successful)
    }

    @Test
    fun `MintHelper examples`() = runBlocking {

        val did = userWallet.bech32Address
        val amount = StdCoin(denom = "uccc", amount = "20")

        // --- Optional
        val fee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "10000")))
        val mode = TxHelper.BroadcastingMode.BROADCAST_MODE_BLOCK

        // --- MintCcc
        val mintCccs = MintCcc(depositAmount = listOf(amount), depositorDid = did, id = UUID.randomUUID().toString())
        //mintCccsList(mintCccs = listOf(mintCccs), wallet = userWallet, fee = fee, mode = mode)

        // --- getExchangeTradePositions
        //val etps = MintHelper.getExchangeTradePositions(userWallet)

        // --- BurnCcc
        val burnCccs = BurnCcc(amount = amount, signerDid = did, id = UUID.randomUUID().toString())
        //burnCccsList(burnCccs = listOf(burnCccs), wallet = userWallet, fee = fee, mode = mode)
    }


    /**
     * Shows how to mint Commercio Cash Credit (CCC).
     */
    private suspend fun mintCccsList(
        mintCccs: List<MintCcc>,
        wallet: Wallet,
        fee: StdFee,
        mode: TxHelper.BroadcastingMode
    ) {
        val response = MintHelper.mintCccsList(
            mintCccs = mintCccs,
            wallet = wallet,
            fee = fee,
            mode = mode
        )
        assertTrue(response is TxResponse.Successful)
    }

    /**
     * Shows how to burn previously minted Commercio Cash Credit (CCC).
     */
    private suspend fun burnCccsList(
        burnCccs: List<BurnCcc>,
        wallet: Wallet,
        fee: StdFee,
        mode: TxHelper.BroadcastingMode
    ) {
        val response = MintHelper.burnCccsList(
            burnCccs = burnCccs,
            wallet = wallet,
            fee = fee,
            mode = mode
        )
        assertTrue(response is TxResponse.Successful)
    }

    @Test
    fun `KycHelper examples`() = runBlocking {
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

        // --- Optional
        val fee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "10000")))
        val mode = TxHelper.BroadcastingMode.BROADCAST_MODE_BLOCK

        // --- Invite user
        val inviteUser = InviteUserHelper.fromWallet(
            wallet = userWallet, // user with membership
            recipientDid = Did(newUserWallet.bech32Address)
        )

        //val responseInviteUser = inviteUsersList( inviteUsers = listOf(inviteUser), wallet = userWallet, mode = mode, fee = fee)

        // --- Need coin to buy membership

        val msg = listOf(
            MsgSend(
                amount = listOf(
                    StdCoin(denom = "uccc", amount = "100"),
                    StdCoin(denom = "ucommercio", amount = "20000")
                ),
                fromAddress = userWallet.bech32Address,
                toAddress = newUserWallet.bech32Address
            )
        )

        //val responseSendCoin = TxHelper.createSignAndSendTx(msgs = msg,wallet = userWallet)

        // --- Buy a membership
        val buyMemberships = BuyMembership(
            buyerDid = newUserWallet.bech32Address,
            membershipType = MembershipType.GOLD,
            tsp = userWallet.bech32Address
        )

        // val responseBuy = buyMembershipsList( buyMemberships = listOf(buyMemberships), wallet = userWallet, fee = fee, mode = mode )

        // --- Get Reward Pool Deposits List
        val rewardPoolDeposit = RewardPoolDepositHelper.fromWallet(
            wallet = userWallet,
            amount = listOf(StdCoin(denom = "ucommercio", amount = "5"))
        )

        //val responseRewardPoolDeposit = KycHelper.rewardPoolDepositsList(wallet = userWallet,rewardPoolDeposits = listOf(rewardPoolDeposit))
    }

    /**
     * Shows how to perform the transaction that allows to invite the given [inviteUsers] users list.
     * Optionally [fee] and broadcasting [mode] parameters can be specified.
     */
    private suspend fun inviteUsersList(
        inviteUsers: List<InviteUser>,
        wallet: Wallet,
        fee: StdFee,
        mode: TxHelper.BroadcastingMode
    ) {
        val response = KycHelper.inviteUsersList(inviteUsers, wallet, fee, mode)
        assertTrue(response is TxResponse.Successful)
    }

    /**
     * Shows how to perform the transaction that allows to buy the membership
     * with the given [buyMemberships] memberships list.
     * Optionally [fee] and broadcasting [mode] parameters can be specified.
     */
    private suspend fun buyMembershipsList(
        buyMemberships: List<BuyMembership>,
        wallet: Wallet,
        fee: StdFee,
        mode: TxHelper.BroadcastingMode
    ) {
        val response = KycHelper.buyMembershipsList(buyMemberships, wallet, fee, mode)
        assertTrue(response is TxResponse.Successful)
    }

    /**
     * Deposit a list of [rewardPoolDeposits] deposits into reward pool
     * with the depositor [wallet].
     * Optionally [fee] and broadcasting [mode] parameters can be specified.
     */
    private suspend fun rewardPoolDepositsList(
        rewardPoolDeposits: List<RewardPoolDeposit>,
        wallet: Wallet,
        fee: StdFee,
        mode: TxHelper.BroadcastingMode
    ) {
        val response = KycHelper.rewardPoolDepositsList(rewardPoolDeposits, wallet, fee, mode)
        assertTrue(response is TxResponse.Successful)
    }
}

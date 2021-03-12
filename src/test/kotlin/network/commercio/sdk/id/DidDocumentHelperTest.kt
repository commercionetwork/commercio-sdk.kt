package network.commercio.sdk

import kotlinx.coroutines.runBlocking
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.Wallet
import network.commercio.sdk.crypto.KeysHelper
import network.commercio.sdk.crypto.SignHelper
import network.commercio.sdk.entities.id.DidDocument
import network.commercio.sdk.entities.id.DidDocumentProof
import network.commercio.sdk.entities.id.DidDocumentPublicKey
import network.commercio.sdk.id.DidDocumentHelper
import network.commercio.sdk.id.DidDocumentProofSignatureContent
import network.commercio.sdk.utils.getTimeStamp
import org.bouncycastle.util.encoders.Base64
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.nio.charset.Charset


@Suppress("EXPERIMENTAL_API_USAGE")
class DidDocumentHelperTest {

    private val info = NetworkInfo(bech32Hrp = "did=com=", lcdUrl = "")

    val mnemonicString =
        "dash ordinary anxiety zone slot rail flavor tortoise guilt divert pet sound ostrich increase resist short ship lift town ice split payment round apology"
    val mnemonic = mnemonicString.split(" ")
    val wallet = Wallet.derive(mnemonic, info)

    val rsaPubKeyVerification = KeysHelper.generateRsaKeyPair()
    val rsaPubKeySignature = KeysHelper.generateRsaKeyPair(type = "RsaSignatureKey2018")

    val verificationPubKey = DidDocumentPublicKey(
        id = "${wallet.bech32Address}#keys-1",
        type = "RsaVerificationKey2018",
        controller = wallet.bech32Address,
        publicKeyPem = """-----BEGIN PUBLIC KEY-----
${Base64.encode(rsaPubKeyVerification.publicWrapper.public.encoded).toString(Charset.defaultCharset())}
-----END PUBLIC KEY-----""".trimMargin()
    )


    val signaturePubKey = DidDocumentPublicKey(
        id = "${wallet.bech32Address}#keys-2",
        type = "RsaSignatureKey2018",
        controller = wallet.bech32Address,
        publicKeyPem = """-----BEGIN PUBLIC KEY-----
${Base64.encode(rsaPubKeySignature.publicWrapper.public.encoded).toString(Charset.defaultCharset())}
-----END PUBLIC KEY-----""".trimMargin()
    )

    val proofSignatureContent = DidDocumentProofSignatureContent(
        context = "https://www.w3.org/ns/did/v1",
        id = wallet.bech32Address,
        publicKeys = listOf(verificationPubKey, signaturePubKey)
    )


    val expectedComputedProof = DidDocumentProof(
        type = "EcdsaSecp256k1VerificationKey2019",
        timeStamp = getTimeStamp(),
        proofPurpose = "authentication",
        controller = wallet.bech32Address,
        verificationMethod = wallet.bech32PublicKey,
        signatureValue = Base64.encode(SignHelper.signSortedTxData(proofSignatureContent, wallet))
            .toString(Charset.defaultCharset())
    )

    val expectedDidDocument = DidDocument(
        context = "https://www.w3.org/ns/did/v1",
        id = wallet.bech32Address,
        publicKeys = listOf(verificationPubKey, signaturePubKey),
        proof = expectedComputedProof,
        service = listOf()
    )


    @Test
    fun `fromWallet builds did document correctly`() = runBlocking {

        val didDocument = DidDocumentHelper.fromWallet(
            wallet = wallet,
            pubKeys = listOf(rsaPubKeyVerification.publicWrapper, rsaPubKeySignature.publicWrapper)
        )

        assertEquals(didDocument.context, expectedDidDocument.context)
        assertEquals(didDocument.id, expectedDidDocument.id)
        assertEquals(didDocument.publicKeys, expectedDidDocument.publicKeys)
        assertEquals(didDocument.proof.type, expectedDidDocument.proof.type)
        assertEquals(didDocument.proof.proofPurpose, expectedDidDocument.proof.proofPurpose)
        assertEquals(didDocument.proof.controller, expectedDidDocument.proof.controller)
        assertEquals(didDocument.proof.verificationMethod, expectedDidDocument.proof.verificationMethod)
        assertNotEquals(didDocument.proof.timeStamp, expectedComputedProof.timeStamp)
        assertEquals(didDocument.proof.signatureValue.length, expectedDidDocument.proof.signatureValue.length)
    }
}
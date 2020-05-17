package network.commercio.sdk.id

import PublicKeyWrapper
import network.commercio.sacco.Wallet
import network.commercio.sacco.encoding.toBase64
import network.commercio.sdk.crypto.SignHelper
import network.commercio.sdk.entities.id.DidDocument
import network.commercio.sdk.entities.id.DidDocumentProof
import network.commercio.sdk.entities.id.DidDocumentPublicKey
import network.commercio.sdk.entities.id.DidDocumentService
import network.commercio.sdk.utils.getTimeStamp
import network.commercio.sdk.utils.toHex
import org.bouncycastle.asn1.ASN1Integer
import org.bouncycastle.asn1.ASN1Sequence
import org.bouncycastle.util.encoders.Base64
import java.io.ByteArrayOutputStream
import java.security.KeyFactory

/**
 * Allows to perform common Did Document related operations.
 */
object DidDocumentHelper {

    /**
     * Creates a [DidDocument] from the given [wallet] and optional [pubKeys].
     */
    fun fromWallet(
        wallet: Wallet,
        pubKeys: List<PublicKeyWrapper> = listOf(),
        service: List<DidDocumentService>? = null
    ): DidDocument {
        if (pubKeys.size < 2) {
            throw Exception("At least two keys are required")
        }

        val keys = pubKeys.mapIndexed { index, key -> convertKey(wallet = wallet, index = index + 1, pubKeyWrapper = key) }

        // Compute the proof
        val proofContent = DidDocumentProofSignatureContent(
            context = "https://www.w3.org/ns/did/v1",
            id = wallet.bech32Address,
            publicKeys = keys
        )

        val verificationMethod = wallet.bech32PublicKey
        val proof = computeProof(proofContent.id, verificationMethod, proofContent, wallet)

        // Build the Did Document
        return DidDocument(
            context = proofContent.context,
            id = proofContent.id,
            publicKeys = proofContent.publicKeys,
            proof = proof,
            service = service
        )
    }

    /**
     * Converts the given [pubKeyWrapper] into a [DidDocumentPublicKey] placed at position [index],
     * @param [wallet] used to get the controller field of each [DidDocumentPublicKey].
     */
    private fun convertKey(pubKeyWrapper: PublicKeyWrapper, index: Int, wallet: Wallet): DidDocumentPublicKey {
        return DidDocumentPublicKey(
            id = "${wallet.bech32Address}#keys-$index",
            type = pubKeyWrapper.type,
            controller = wallet.bech32Address,
            publicKeyPem = when (pubKeyWrapper.type) {
                "RsaVerificationKey2018", "RsaSignatureKey2018" -> {
                    """-----BEGIN PUBLIC KEY-----
${pubKeyWrapper.public.encoded.toBase64()}
-----END PUBLIC KEY-----""".trimMargin()
                }
                "Secp256k1VerificationKey2018" -> {
                    pubKeyWrapper.public.encoded.toBase64()
                }
                "Ed25519VerificationKey2018" -> {
                    ""
                }
                else -> ""
            }
        )
    }

    /**
     * Computes the [DidDocumentProof] based on the given [proofSignatureContent].
     */
    private fun computeProof(
        controller: String,
        verificationMethod: String,
        proofSignatureContent: DidDocumentProofSignatureContent,
        wallet: Wallet,
        proofPurpose: String = "authentication"
    ): DidDocumentProof {
        return DidDocumentProof(
            type = "EcdsaSecp256k1VerificationKey2019",
            iso8601CreationTimeStamp = getTimeStamp(),
            proofPurpose = proofPurpose,
            controller = controller,
            verificationMethod = verificationMethod,
            signatureValue = SignHelper.signSortedTxData(proofSignatureContent, wallet).toBase64()
        )
    }
}

package network.commercio.sdk.id

import network.commercio.sacco.Wallet
import network.commercio.sdk.crypto.SignHelper
import network.commercio.sdk.entities.id.DidDocument
import network.commercio.sdk.entities.id.DidDocumentProof
import network.commercio.sdk.entities.id.DidDocumentPublicKey
import network.commercio.sdk.entities.id.DidDocumentService
import network.commercio.sdk.utils.getTimeStamp
import network.commercio.sdk.utils.toHex
import org.bouncycastle.util.encoders.Base64
import java.security.PublicKey
import java.security.interfaces.ECPublicKey
import java.security.interfaces.RSAPublicKey

/**
 * Allows to perform common Did Document related operations.
 */
object DidDocumentHelper {

    /**
     * Creates a [DidDocument] from the given [wallet] and optional [pubKeys].
     */
    fun fromWallet(
        wallet: Wallet,
        pubKeys: List<PublicKey> = listOf(),
        service: List<DidDocumentService>? = null ): DidDocument {

        if (pubKeys.size < 2) {
            throw Exception("At least two keys are required")
        }

        val keys = pubKeys.mapIndexed { index, key ->
            convertKey(wallet = wallet, index = index + 2, pubKey = key)
        }

     /*
        // Get the authentication key
        val authKeyId = "${wallet.bech32Address}#keys-1"
        val authKey = DidDocumentPublicKey(
            id = authKeyId,
            type = DidDocumentPublicKey.Type.SECP256K1,
            controller = wallet.bech32Address,
            publicKeyPem = wallet.pubKeyAsHex
        )
     */

        // Compute the proof
        val proofContent = DidDocumentProofSignatureContent(
            context = "https://www.w3.org/ns/did/v1",
            id = wallet.bech32Address,
            publicKeys = keys
            //authentication = listOf(authKeyId)
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
     * Converts the given [pubKey] into a [DidDocumentPublicKey] placed at position [index],
     * @param wallet used to get the controller field of each [DidDocumentPublicKey].
     */
    private fun convertKey(pubKey: PublicKey, index: Int, wallet: Wallet): DidDocumentPublicKey {
        return DidDocumentPublicKey(
            id = "${wallet.bech32Address}#keys-$index",
            type = when (pubKey) {
                is RSAPublicKey -> DidDocumentPublicKey.Type.RSA
                is ECPublicKey -> DidDocumentPublicKey.Type.SECP256K1
                else -> DidDocumentPublicKey.Type.ED25519
            },
            controller = wallet.bech32Address,
            publicKeyPem = pubKey.encoded.toHex()
        )
    }

    /**
     * Computes the [DidDocumentProof] based on the given [authKeyid] and [content].
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
            signatureValue = SignHelper.signSorted(proofSignatureContent, wallet).toHex()


        )
    }

}

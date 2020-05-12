package network.commercio.sdk.id

import network.commercio.sacco.Wallet
import network.commercio.sdk.crypto.SignHelper
import network.commercio.sdk.entities.id.DidDocument
import network.commercio.sdk.entities.id.DidDocumentProof
import network.commercio.sdk.entities.id.DidDocumentPublicKey
import network.commercio.sdk.utils.getTimeStamp
import network.commercio.sdk.utils.toHex
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
    fun fromWallet(wallet: Wallet, pubKeys: List<PublicKey> = listOf()): DidDocument {

        // Get the authentication key
        val authKeyId = "${wallet.bech32Address}#keys-1"
        val authKey = DidDocumentPublicKey(
            id = authKeyId,
            type = DidDocumentPublicKey.Type.SECP256K1,
            controller = wallet.bech32Address,
            publicKeyPem = wallet.pubKeyAsHex
        )

        // Compute the proof
        val proofContent = DidDocumentProofSignatureContent(
            context = "https://www.w3.org/ns/did/v1",
            did = wallet.bech32Address,
            publicKeys = listOf(authKey) + pubKeys.mapIndexed { index, key ->
                convertKey(wallet = wallet, index = index + 2, pubKey = key)
            },
            authentication = listOf(authKeyId)
        )
        val proof = computeProof(authKeyId, proofContent, wallet)

        // Build the Did Document
        return DidDocument(
            context = proofContent.context,
            did = proofContent.did,
            publicKeys = proofContent.publicKeys,
            authentication = proofContent.authentication,
            proof = proof,
            services = null
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
    private fun computeProof(authKeyid: String, content: DidDocumentProofSignatureContent, wallet: Wallet): DidDocumentProof {
        return DidDocumentProof(
            type = "LinkedDataSignature2015",
            iso8601CreationTimeStamp = getTimeStamp(),
            creatorKeyId = authKeyid,
            signatureValue = SignHelper.signSorted(content, wallet).toHex()
        )
    }
}

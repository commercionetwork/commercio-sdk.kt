package network.commercio.sdk.entities.id

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.security.KeyFactory
import java.security.PublicKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.*
import java.util.Base64 as B64


/**
 * Commercio network's did document is described here:
 * https://scw-gitlab.zotsell.com/Commercio.network/Cosmos-application/blob/master/Commercio%20Decentralized%20ID%20framework.md
 */


data class DidDocumentWrapper(
    @JsonProperty("owner") val owner: String,
    @JsonProperty("did_document") val didDoc: DidDocument
)


data class DidDocument(
    @JsonProperty("@context") val context: String,
    @JsonProperty("id") val id: String,
    @JsonProperty("publicKey") val publicKeys: List<DidDocumentPublicKey>,
    @JsonProperty("proof") val proof: DidDocumentProof,
    @JsonProperty("service") @JsonInclude(JsonInclude.Include.NON_NULL) val service: List<DidDocumentService>?
) {

    /**
     * Returns the [PublicKey] that should be used as the public encryption key when encrypting data
     * that can later be read only by the owner of this Did Document.
     */
    @get:JsonIgnore
    val encryptionKey: RSAPublicKey?
        get() {
            // Find the encryption key
            return publicKeys.firstOrNull { it.type == "RsaVerificationKey2018" || it.type == "RsaSignatureKey2018" }?.let {
                val pubKeySpec = X509EncodedKeySpec(B64.getDecoder().decode(it.publicKeyPem.replace("\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")))
                KeyFactory.getInstance("RSA").generatePublic(pubKeySpec) as RSAPublicKey
            }
        }
}

package network.commercio.sdk.entities.id

import com.fasterxml.jackson.annotation.JsonProperty
import org.spongycastle.util.encoders.Hex
import java.security.KeyFactory
import java.security.PublicKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec

/**
 * Commercio network's did document is described here:
 * https://scw-gitlab.zotsell.com/Commercio.network/Cosmos-application/blob/master/Commercio%20Decentralized%20ID%20framework.md
 */
data class DidDocument(
    @field:JsonProperty("@context") val context: String,
    @field:JsonProperty("id") val did: String,
    @field:JsonProperty("publicKey") val publicKeys: List<DidDocumentPublicKey>,
    @field:JsonProperty("authentication") val authentication: List<String>,
    @field: JsonProperty("proof") val proof: DidDocumentProof,
    @field: JsonProperty("service") val services: List<DidDocumentService>
) {

    /**
     * Returns the [PublicKey] that should be used as the public encryption key when encrypting data
     * that can later be read only by the owner of this Did Document.
     */
    val encryptionKey: RSAPublicKey?
        get() {
            // Find the encryption key
            return publicKeys.firstOrNull { it.type == DidDocumentPublicKey.Type.RSA }?.let {
                val pubKeySpec = PKCS8EncodedKeySpec(Hex.decode(it.publicKeyHex))
                KeyFactory.getInstance("RSA").generatePublic(pubKeySpec) as RSAPublicKey
            }
        }
}

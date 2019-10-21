package network.commercio.sdk.entities.id

import com.fasterxml.jackson.annotation.JsonProperty
import org.spongycastle.util.encoders.Hex
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec

/**
 * Commercio network's did document is described here:
 * https://scw-gitlab.zotsell.com/Commercio.network/Cosmos-application/blob/master/Commercio%20Decentralized%20ID%20framework.md
 */
data class DidDocument(
    @field:JsonProperty("@context") val context: String,
    @field:JsonProperty("id") val did: String,
    @field:JsonProperty("publicKey") val publicKeys: List<DidDocumentPublicKey>
) {

    val encryptionKey: PublicKey?
        get() {
            // Find the encryption key
            return publicKeys.firstOrNull { it.type == DidDocumentPublicKey.Type.RSA }?.let {
                val pubKeySpec = PKCS8EncodedKeySpec(Hex.decode(it.publicKeyHex))
                KeyFactory.getInstance("RSA").generatePublic(pubKeySpec)
            }
        }

}

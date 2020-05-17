package network.commercio.sdk.crypto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import network.commercio.sacco.Wallet
import network.commercio.sdk.entities.id.DidDocument
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.PublicKey
import java.security.Security
import java.security.Signature
import java.security.interfaces.ECPublicKey
import java.security.interfaces.RSAPublicKey
import java.security.interfaces.RSAPrivateKey

/**
 * Allows to easily perform signature-related operations.
 */
object SignHelper {

    init {
        Security.insertProviderAt(BouncyCastleProvider(), 1)
    }

    /**
     * Object mapper that allows to properly serialize any object sorting its keys alphabetically
     * so that it can be properly signed and verified against an existing signature.
     */
    private val objectMapper = jacksonObjectMapper().apply {
        configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
        configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
        setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
    }

    /**
     * Takes the given [data], converts it to an JSON object and signs its content
     * using the given [wallet].
     */
    fun signSortedTxData(data: Any, wallet: Wallet): ByteArray {
        val objectMapper = jacksonObjectMapper().apply {
            configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, false)
            configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, false)
            setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
        }
        val jsonSignData = objectMapper.writeValueAsString(data)
        return wallet.signTxData(jsonSignData.toByteArray(Charsets.UTF_8))
    }

    /**
     * Takes the given [data], converts it to an alphabetically sorted JSON object and signs its content
     * using the given [wallet].
     */
    fun signSorted(data: Any, wallet: Wallet): ByteArray {
        val jsonSignData = objectMapper.writeValueAsString(data)
        return wallet.sign(jsonSignData.toByteArray(Charsets.UTF_8))
    }

    /**
     * Sign in PKCS1 v 1.5 with private Key the payload
     */
    fun signPowerUpSignature(senderDid: String, pairwiseDid: String, timestamp: String, privateKey: RSAPrivateKey): ByteArray {
        val concat = senderDid + pairwiseDid + timestamp
        
        val s = Signature.getInstance("SHA256WithRSA")
        .apply {
            initSign(privateKey)
            update(concat.toByteArray(charset("UTF-8")))
        }
        val signature: ByteArray = s.sign()
        return signature
    }

    /**
     * Verifies that the given [signature] is valid for the given [signedData] against the given [key].
     */
    private fun verifySignature(signature: ByteArray, signedData: ByteArray, key: PublicKey): Boolean {
        val signatureType = when (key) {
            is ECPublicKey -> "SHA256WithECDSA"
            is RSAPublicKey -> "SHA256WithRSA"
            else -> throw UnsupportedOperationException("Invalid public key type when checking the signature")
        }
        return Signature.getInstance(signatureType, BouncyCastleProvider()).apply {
            initVerify(key)
            update(signedData)
        }.verify(signature)
    }

    /**
     * Verifies that the given [signature] is valid for the given [signedData] by using the public keys
     * contained inside the given [didDocument].
     */
    fun verifySignature(signature: ByteArray, signedData: Any, didDocument: DidDocument): Boolean {
        val signedDataBytes = jacksonObjectMapper().writeValueAsString(signedData).toByteArray(Charsets.UTF_8)
        return didDocument.publicKeys
            .mapNotNull { it.toPublicKey() }
            .any { verifySignature(signature, signedDataBytes, it) }
    }
}
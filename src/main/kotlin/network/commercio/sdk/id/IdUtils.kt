package network.commercio.sdk.id

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import network.commercio.sdk.crypto.EncryptionHelper
import network.commercio.sdk.crypto.KeysHelper
import java.text.SimpleDateFormat
import java.util.*

fun getTimeStamp(): String {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault()).format(Date())
}

class ProofGenerationResult(val encryptedProof: ByteArray, val encryptedAesKey: ByteArray)

suspend fun generateProof(payload: Any): ProofGenerationResult {
    // Generate the AES key
    val aesKey = KeysHelper.generateAesKey()

    // Encrypt the payload
    val encryptionData = jacksonObjectMapper().writeValueAsString(payload)
    val encryptedPayload = EncryptionHelper.encryptWithAes(encryptionData, aesKey)

    // Encrypt the AES key
    val rsaKey = EncryptionHelper.getGovernmentRsaPubKey()
    val encryptedAesKey = EncryptionHelper.encryptWithRsa(aesKey.encoded, rsaKey)

    return ProofGenerationResult(
        encryptedProof = encryptedPayload,
        encryptedAesKey = encryptedAesKey
    )
}
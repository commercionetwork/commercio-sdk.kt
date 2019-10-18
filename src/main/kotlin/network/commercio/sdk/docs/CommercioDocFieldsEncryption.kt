package network.commercio.sdk.docs

import network.commercio.sacco.Wallet
import network.commercio.sdk.crypto.EncryptionHelper
import network.commercio.sdk.crypto.KeysHelper
import network.commercio.sdk.entities.Did
import network.commercio.sdk.id.IdHelper
import network.commercio.sdk.entities.docs.CommercioDoc
import network.commercio.sdk.utils.toHex
import org.spongycastle.util.encoders.Hex
import javax.crypto.Cipher

enum class EncryptedData {
    CONTENT,
    CONTENT_URI,
    METADATA_CONTENT_URI,
    METADATA_SCHEMA_URI;

    override fun toString(): String {
        return when (this) {
            CONTENT -> "content"
            CONTENT_URI -> "content_uri"
            METADATA_CONTENT_URI -> "metadata.content_uri"
            METADATA_SCHEMA_URI -> "metadata.schema.uri"
        }
    }
}

internal suspend fun CommercioDoc.encryptField(
    encryptedData: List<EncryptedData>,
    recipients: List<Did>,
    wallet: Wallet
): CommercioDoc {

    // -----------------
    // --- Encryption
    // -----------------

    // Create an AES encryption key
    val aesKey = KeysHelper.generateAesKey()

    // Encrypt the contents
    val encryptedContentUri = when (encryptedData.contains(EncryptedData.CONTENT_URI)) {
        true -> EncryptionHelper.encryptWithAes(contentUri, aesKey).toHex()
        false -> null
    }

    val encryptedMetadataContentUri = when (encryptedData.contains(EncryptedData.METADATA_CONTENT_URI)) {
        true -> EncryptionHelper.encryptWithAes(metadata.contentUri, aesKey).toHex()
        false -> null
    }

    val encryptedMetadataSchemaUri = when (encryptedData.contains(EncryptedData.METADATA_SCHEMA_URI)) {
        true -> metadata.schema?.uri?.let { EncryptionHelper.encryptWithAes(it, aesKey).toHex() }
        false -> null
    }

    // ---------------------
    // --- Keys creation
    // ---------------------

    // Get the recipients Did Documents
    val recipientsDidDocs = recipients.mapNotNull { IdHelper.getDidDocument(it, wallet) }

    // Get a list of al the Did Documents and the associated encryption key
    val keys = recipientsDidDocs
        .map { it to it.encryptionKey }
        .map { (didDoc, encKey) -> Pair(didDoc, encKey) }
        .filter { it.second != null }


    // Create the encryption key field
    val encryptionKeys = keys.map { (didDoc, encryptionKey) ->
        val encryptedAesKey = Cipher.getInstance("RSAWithSHA256").apply {
            init(Cipher.ENCRYPT_MODE, encryptionKey)
        }.doFinal(aesKey.encoded)

        CommercioDoc.EncryptionData.Key(recipientDid = didDoc.did, value = Hex.toHexString(encryptedAesKey))
    }

    // Return a copy of the document
    return this.copy(
        contentUri = encryptedContentUri ?: contentUri,
        metadata = metadata.copy(
            contentUri = encryptedMetadataContentUri ?: metadata.contentUri,
            schema = metadata.schema?.copy(
                uri = encryptedMetadataSchemaUri ?: metadata.schema.uri
            )
        ),
        encryptionData = CommercioDoc.EncryptionData(
            keys = encryptionKeys,
            encryptedData = encryptedData.map { it.toString() }
        )
    )
}
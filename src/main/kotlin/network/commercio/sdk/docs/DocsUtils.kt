package network.commercio.sdk.docs

import network.commercio.sacco.Wallet
import network.commercio.sdk.crypto.EncryptionHelper
import network.commercio.sdk.entities.docs.CommercioDoc
import network.commercio.sdk.entities.docs.EncryptedData
import network.commercio.sdk.entities.id.Did
import network.commercio.sdk.id.IdHelper
import network.commercio.sdk.utils.toHex
import javax.crypto.SecretKey


/**
 * Transforms [this] document into one having the proper fields encrypted as specified inside the [encryptedData] list.
 * All the fields will be encrypted using the specified [aesKey]. This key will later be encrypted for each and every
 * Did specified into the [recipients] list.
 * The overall encrypted data will be put inside the proper document field.
 * Throws [ArgumentError] if:
 * Is provided [CommercioEncryptedData.CONTENT_URI] without a valid [contentUri].
 * Is provided [CommercioEncryptedData.METADATA_SCHEMA_URI] without a  [schema].
 */
internal suspend fun CommercioDoc.encryptField(
    aesKey: SecretKey,
    encryptedData: List<EncryptedData>,
    recipients: List<Did>,
    wallet: Wallet
): CommercioDoc {

    // -----------------
    // --- Encryption
    // -----------------

    // Encrypt the contents
    val encryptedContentUri = when (encryptedData.contains(EncryptedData.CONTENT_URI)) {
        true -> {
            require(!contentUri.isNullOrBlank()) { "Document contentUri field can not be null or empty if the encryptedData arguments contains EncryptedData.CONTENT_URI" }
            EncryptionHelper.encryptWithAes(contentUri, aesKey).toHex()
        }
        false -> null
    }

    val encryptedMetadataContentUri = when (encryptedData.contains(EncryptedData.METADATA_CONTENT_URI)) {
        true -> EncryptionHelper.encryptWithAes(metadata.contentUri, aesKey).toHex()
        false -> null
    }

    val encryptedMetadataSchemaUri = when (encryptedData.contains(EncryptedData.METADATA_SCHEMA_URI)) {
        true -> {
            requireNotNull(metadata.schema?.uri) { "Document metadata.schema field can not be null if the encryptedData arguments contains CommercioEncryptedData.METADATA_SCHEMA_URI" }
            metadata.schema?.uri?.let { EncryptionHelper.encryptWithAes(it, aesKey).toHex() }
        }
        false -> null
    }

    // ---------------------
    // --- Keys creation
    // ---------------------


    // Get the recipients Did Documents
    val recipientsDidDocs = recipients.mapNotNull { IdHelper.getDidDocument(it, wallet) }


    // Get a list of al the Did Documents and the associated encryption key
    val keys = recipientsDidDocs
        .mapNotNull { did ->
            // Returns null if the key is null, a Pair(Did, Key) otherwise
            did.encryptionKey?.let { key -> did to key }
        }
        .map { (didDoc, encKey) -> Pair(didDoc, encKey) }


    // Create the encryption key field
    val encryptionKeys: List<CommercioDoc.EncryptionData.Key> = keys.map { (didDoc, pubKey) ->
        val encryptedAesKey = EncryptionHelper.encryptWithRsa(aesKey.encoded, pubKey)
        CommercioDoc.EncryptionData.Key(recipientDid = didDoc.id, value = encryptedAesKey.toHex())
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
            encryptedData = encryptedData.map { it }.toList()
        )
    )
}
package network.commercio.sdk.entities.docs

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import network.commercio.sdk.utils.getStringBytes
import network.commercio.sdk.utils.matchBech32Format
import network.commercio.sdk.utils.matchUuidv4

/**
 * Contains all the data related to a document that is sent to the chain when a user wants to share
 * a document with another user.
 */
data class CommercioDoc(
    @JsonProperty("sender") val senderDid: String,
    @JsonProperty("recipients") val recipientsDids: List<String>,
    @JsonProperty("UUID") val uuid: String,
    @JsonProperty("contentUri") @JsonInclude(JsonInclude.Include.NON_EMPTY) val contentUri: String = "",
    @JsonProperty("metadata") val metadata: Metadata,
    @JsonProperty("checksum") @JsonInclude(JsonInclude.Include.NON_NULL) val checksum: Checksum? = null,
    @JsonProperty("encryption_data") @JsonInclude(JsonInclude.Include.NON_EMPTY) val encryptionData: EncryptionData? = null,
    @JsonProperty("do_sign") @JsonInclude(JsonInclude.Include.NON_EMPTY) val doSign: CommercioDoSign? = null
) {

    init {
        require(matchBech32Format(senderDid)) { "sender requires a valid Bech32 format" }
        require(recipientsDids.isNotEmpty() && recipientsDids.all { matchBech32Format(it) }
        ) { "recipients must contains a non empty list of string in Bech32 format" }
        require(matchUuidv4(uuid)) { "uuid requires a valid UUID v4 format" }
        require(getStringBytes(contentUri) <= 512) { "contentUri must have a valid length" }
        require(
            _checksumMustBePresentIfDoSign(
                checksum,
                doSign
            )
        ) { "when doSign is provided then also the checksum field is required" }
    }

    /**
     * Return [true] if the fields [doSign] and [checksum] are both not null if the
     * first is specified.
     */

    fun _checksumMustBePresentIfDoSign(
        checksum: CommercioDoc.Checksum?,
        doSign: CommercioDoc.CommercioDoSign?
    ): Boolean {
        if (doSign != null) {
            return checksum != null
        }
        return true;
    }


    data class Metadata(
        @JsonProperty("content_uri") val contentUri: String,
        @JsonProperty("schema") val schema: Schema? = null,
        @JsonProperty("schema_type") @JsonInclude(JsonInclude.Include.NON_EMPTY) val schemaType: String = ""

    ) {
        init {
            require(getStringBytes(contentUri) <= 512) { "metadata.content_uri must have a valid length" }
            require(
                schemaType.isNotEmpty() && getStringBytes(schemaType) <= 512 || schema != null
            ) { "schemaType must have a valid length or schema must not be null" }
        }

        data class Schema(
            @JsonProperty("uri") val uri: String,
            @JsonProperty("version") val version: String
        ) {
            init {
                require(uri.isNotEmpty() && getStringBytes(uri) <= 512) { "metadata.schema.uri must have a valid length" }
                require(version.isNotEmpty() && getStringBytes(version) <= 32) { "metadata.schema.version must have a valid length" }
            }
        }
    }

    data class Checksum(
        @JsonProperty("value") val value: String,
        @JsonProperty("algorithm") val algorithm: Algorithm
    ) {
        init {
            require(value.isNotEmpty()) { "checksum.value must not be empty" }
        }

        enum class Algorithm {
            @JsonProperty("md5")
            MD5,

            @JsonProperty("sha-1")
            SHA1,

            @JsonProperty("sha-224")
            SHA224,

            @JsonProperty("sha-384")
            SHA384,

            @JsonProperty("sha-512")
            SHA512,

            @JsonProperty("sha-256")
            SHA256
        }
    }

    data class EncryptionData(
        @JsonProperty("keys") val keys: List<Key>,
        @JsonProperty("encrypted_data") val encryptedData: List<EncryptedData>
    ) {

        data class Key(
            @JsonProperty("recipient") val recipientDid: String,
            @JsonProperty("value") val value: String
        ) {
            init {
                require(getStringBytes(value) <= 512) { "encryption_data.keys.*.value must have a valid length" }
            }
        }
    }

    data class CommercioDoSign(
        @JsonProperty("storage_uri") val storageUri: String,
        @JsonProperty("signer_instance") val signerIstance: String,
        @JsonProperty("vcr_id") val vcrId: String,
        @JsonProperty("certificate_profile") val certificateProfile: String,
        @JsonProperty("sdn_data") val sdnData: List<CommercioSdnData>? = null
    ) {
        init {
            require(getStringBytes(vcrId) <= 64) { "do_sign.vcr_id must have a valid length" }
            require(getStringBytes(certificateProfile) <= 32) { "do_sign.certificate_profile must have a valid length" }
            require(sdnData == null || sdnData?.isNotEmpty()) { "do_sign.sdn_data must be not empty" }
        }


        enum class CommercioSdnData {
            @JsonProperty("common_name")
            COMMON_NAME,

            @JsonProperty("surname")
            SURNAME,

            @JsonProperty("serial_number")
            SERIAL_NUMBER,

            @JsonProperty("given_name")
            GIVEN_NAME,

            @JsonProperty("organization")
            ORGANIZATION,

            @JsonProperty("country")
            COUNTRY;
        }
    }
}

/**
 * For more information see:
 * https://docs.commercio.network/x/docs/#supported-encrypted-data
 */

enum class EncryptedData {
    /**
     * Special identifier, references the document's file contents. Means that
     * the `aes_key` has been used to encrypt a file exchanged by other means of
     * communication.
     */
    @JsonProperty("content")
    CONTENT,

    @JsonProperty("content_uri")
    CONTENT_URI,

    @JsonProperty("metadata.content_uri")
    METADATA_CONTENT_URI,

    @JsonProperty("metadata.schema.uri")
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
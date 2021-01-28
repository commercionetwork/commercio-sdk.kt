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
    @JsonProperty("uuid") val uuid: String,
    @JsonProperty("content_uri") @JsonInclude(JsonInclude.Include.NON_EMPTY) val contentUri: String = "",
    @JsonProperty("metadata") val metadata: Metadata,
    @JsonProperty("checksum") val checksum: Checksum? = null,
    @JsonProperty("encryption_data") @JsonInclude(JsonInclude.Include.NON_EMPTY) val encryptionData: EncryptionData? = null,
    @JsonProperty("do_sign") @JsonInclude(JsonInclude.Include.NON_EMPTY) val doSign: CommercioDoSign? = null
) {

    init {
        require(matchBech32Format(senderDid)) { "sender requires a valid Bech32 format" }
        require(recipientsDids.isNotEmpty() && recipientsDids.all { matchBech32Format(it) }
        ) { "recipients must contains a non empty list of string in Bech32 format" }
        require(matchUuidv4(uuid)) { "uuid requires a valid UUID v4 format" }
        require(contentUri.isNullOrEmpty() || getStringBytes(contentUri) <= 512) { "contentUri must have a valid length" }
    }


    data class Metadata(
        @JsonProperty("content_uri") val contentUri: String,
        @JsonProperty("schema") val schema: Schema? = null,
        @JsonProperty("schema_type") @JsonInclude(JsonInclude.Include.NON_EMPTY) val schemaType: String = ""

    ) {
        init {
            require(getStringBytes(contentUri) <= 512) { "metadata.content_uri must have a valid length" }
            require(
                !schemaType.isNullOrEmpty() && getStringBytes(schemaType) <= 512 || schema != null
            ) { "schemaType must have a valid length or schema must not be null" }
        }

        data class Schema(
            @JsonProperty("uri") val uri: String,
            @JsonProperty("version") val version: String
        ) {
            init {
                require(getStringBytes(uri) <= 512) { "metadata.schema.uri must have a valid length" }
                require(getStringBytes(version) <= 32) { "metadata.schema.version must have a valid length" }
            }
        }
    }

    data class Checksum(
        @JsonProperty("value") val value: String,
        @JsonProperty("algorithm") val algorithm: Algorithm
    ) {

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
        @JsonProperty("encrypted_data") val encryptedData: List<String>
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
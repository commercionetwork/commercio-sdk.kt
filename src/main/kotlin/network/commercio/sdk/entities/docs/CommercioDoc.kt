package network.commercio.sdk.entities.docs

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Contains all the data related to a document that is sent to the chain when a user wants to share
 * a document with another user.
 */
data class CommercioDoc(
    @JsonProperty("sender") val senderDid: String,
    @JsonProperty("recipients") val recipientsDids: List<String>,
    @JsonProperty("uuid") val uuid: String,
    @JsonProperty("content_uri") val contentUri: String = "",
    @JsonProperty("metadata") val metadata: Metadata,
    @JsonProperty("checksum") val checksum: Checksum? = null,
    @JsonProperty("encryption_data") val encryptionData: EncryptionData?= null,
    @JsonProperty("do_sign") val doSign: CommercioDoSign? = null
)  {

    data class Metadata(
        @JsonProperty("content_uri") val contentUri: String,
        @JsonProperty("schema") val schema: Schema? = null,
        @JsonProperty("schema_type") val schemaType: String = ""

    ) {
        data class Schema(
            @JsonProperty("uri") val uri: String,
            @JsonProperty("version") val version: String
        )
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
            SHA512
        }
    }

    data class EncryptionData(
        @JsonProperty("keys") val keys: List<Key>,
        @JsonProperty("encrypted_data") val encryptedData: List<String>
    ) {

        data class Key(
            @JsonProperty("recipient") val recipientDid: String,
            @JsonProperty("value") val value: String
        )
    }

    data class CommercioDoSign(
        @JsonProperty("storage_uri") val storageUri: String,
        @JsonProperty("signer_instance") val signerInstance: String,
        @JsonProperty("sdn_data") val sdnData: List<CommercioSdnData>?= null,
        @JsonProperty("vcr_id") val vcrId: String,
        @JsonProperty("certificate_profile") val certificateProfile: String
    ) {

        data class CommercioSdnData(
            @JsonProperty("common_name") val COMMON_NAME: String ="",
            @JsonProperty("surname") val SURNAME: String="",
            @JsonProperty("serial_number") val SERIAL_NUMBER: String="",
            @JsonProperty("given_name") val GIVEN_NAME: String="",
            @JsonProperty("organization") val ORGANIZATION: String="",
            @JsonProperty("country") val COUNTRY: String=""
        )
    }
}
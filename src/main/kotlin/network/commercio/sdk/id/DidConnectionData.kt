package network.commercio.sdk.id

import com.fasterxml.jackson.annotation.JsonProperty
import network.commercio.sdk.id.DidConnectionData.Payload

/**
 * Contains the data that should be sent to another user when creating a pairwise connection with him.
 * @property encryptedAesKey AES-256 key that has been encrypted with the recipient's public encryption key.
 * @property encryptedData AES-encrypted, JSON-serialized [Payload].
 */
data class DidConnectionData(
    @JsonProperty("encrypted_aes_key") val encryptedAesKey: String,
    @JsonProperty("encrypted_data") val encryptedData: String
) {

    /**
     * Contains the data that must be sent to another user when creating a pairwise connection with him.
     */
    data class Payload(
        @JsonProperty("public_did") val publicDid: String,
        @JsonProperty("timestamp") val timeStamp: String,
        @JsonProperty("pairwise_did") val pairwiseDid: String,
        @JsonProperty("signature") val signature: String
    ) {

        /**
         * Represents the JSON object that should be created, signed and put inside a [Payload] instance.
         */
        data class SignatureJson(
            @JsonProperty("pairwise_did") val pairwiseDid: String,
            @JsonProperty("timestamp") val timeStamp: String
        )
    }
}



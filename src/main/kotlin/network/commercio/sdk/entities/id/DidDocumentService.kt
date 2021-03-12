package network.commercio.sdk.entities.id

import com.fasterxml.jackson.annotation.JsonProperty
import network.commercio.sdk.utils.getStringBytes

/**
 * Contains the data of a service that can accept the Did Document as authentication method or something else.
 */
data class DidDocumentService(
    @JsonProperty("id") val id: String,
    @JsonProperty("type") val type: String,
    @JsonProperty("serviceEndpoint") val endpoint: String
) {
    init {
        require(getStringBytes(id) <= 64) { "service.id must have a valid length" }
        require(getStringBytes(type) <= 64) { "service.type must have a valid length" }
        require(getStringBytes(endpoint) <= 512) { "service.serviceEndpoint must have a valid length" }
    }
}
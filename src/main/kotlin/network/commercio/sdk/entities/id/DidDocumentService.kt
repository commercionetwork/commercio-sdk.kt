package network.commercio.sdk.entities.id

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Contains the data of a service that can accept the Did Document as authentication method or something else.
 */
data class DidDocumentService(
    @JsonProperty("id") val id: String,
    @JsonProperty("type") val type: String,
    @JsonProperty("serviceEndpoint") val endpoint: String
)
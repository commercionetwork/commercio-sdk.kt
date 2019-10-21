package network.commercio.sdk.networking

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents the result that is returned when querying some data from a full node.
 */
data class QueryResult<T>(
    @JsonProperty("height") val height: String,
    @JsonProperty("result") val result: T
)
package network.commercio.sdk.networking

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import network.commercio.sdk.utils.tryOrNull

/**
 * Allows to easily perform network-related operations.
 */
internal object Network {

    /**
     * Default client that must be used when performing network requests.
     *
     * Notes. Internal for testing.
     */
    internal val client: HttpClient by lazy {
        HttpClient {
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }
        }
    }

    /**
     * Queries the given [url] and returns an object of type [T], or `null` if some error raised.
     */
    suspend fun <T> query(url: String): T? = tryOrNull {
        client.get<QueryResult<T>>(url).result
    }
}
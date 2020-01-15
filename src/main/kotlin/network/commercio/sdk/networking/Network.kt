package network.commercio.sdk.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import network.commercio.sdk.utils.tryOrNull
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Allows to easily perform network-related operations.
 */
object Network {

    /**
     * Default client that must be used when performing network requests.
     *
     * Notes. Internal for testing.
     */
    val client: HttpClient by lazy {
        HttpClient(OkHttp) {
            engine {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(loggingInterceptor)
            }
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }
        }
    }

    /**
     * Queries the given [url] and returns an object of type [T], or `null` if some error raised.
     */
    suspend fun <T> queryChain(url: String): T? = tryOrNull {
        client.get<QueryResult<T>>(url).result
    }

    /**
     * Gets the contents located at the given [url] as an object of type [T].
     * If something goes wrong, returns `null` instead.
     */
    suspend inline fun <reified T> get(url: String): T? = tryOrNull {
        client.get<T>(url)
    }
}
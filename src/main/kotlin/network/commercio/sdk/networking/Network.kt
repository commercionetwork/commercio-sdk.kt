package network.commercio.sdk.networking

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature

object Network {

    val client: HttpClient by lazy {
        return@lazy HttpClient {
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }
        }
    }

}
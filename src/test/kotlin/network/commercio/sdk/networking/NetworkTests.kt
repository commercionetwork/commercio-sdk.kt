package network.commercio.sdk.networking

import com.fasterxml.jackson.annotation.JsonProperty
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.http.ContentType
import io.ktor.http.headersOf
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.runBlocking
import network.commercio.sdk.readResource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Tests for [Network].
 */
class NetworkTests {

    data class TestData(
        @JsonProperty("sender") val sender: String,
        @JsonProperty("uuid") val uuid: String
    )

    @Test
    fun `query returns the correct data`() = mockkObject(Network) {
        val mockClient = HttpClient(MockEngine) {
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }

            engine {
                addHandler {
                    val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                    val response = readResource("sent_documents_response.json")
                    respond(response, headers = responseHeaders)
                }
            }
        }
        every { Network.client } returns mockClient

        val result = runBlocking { Network.queryChain<List<TestData>>("http://example.com") }
        assertNotNull(result)
        assertEquals(4, result?.size)
    }


}
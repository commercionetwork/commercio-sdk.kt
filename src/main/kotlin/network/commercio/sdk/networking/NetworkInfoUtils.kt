package network.commercio.sdk.networking

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import network.commercio.sacco.NetworkInfo

suspend fun NetworkInfo.fetchNetworkInfo(): NodeInfoResponse {
    val nodeInfoUri = "${this.lcdUrl}/node_info"
    val response: Map<String, Any> = Network.get<Map<String, Any>>(nodeInfoUri)
        ?: throw Exception("Could not find node info")

    val nodeInfoResponse: NodeInfoResponse =
        jacksonObjectMapper().convertValue(response, NodeInfoResponse::class.java)
    return nodeInfoResponse
}

suspend fun NetworkInfo.isVersion(version: String): Boolean {
    require(version.isNotEmpty()) { "The version must not be empty. An valid example is '2.2'" }
    val nodeInfoResponse = this.fetchNetworkInfo()
    print("version: ${nodeInfoResponse.applicationVersion.version}")
    return nodeInfoResponse.applicationVersion.version.contains(version)
}

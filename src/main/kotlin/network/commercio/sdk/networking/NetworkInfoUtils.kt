package network.commercio.sdk.networking

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import network.commercio.sacco.NetworkInfo


suspend fun NetworkInfo.fetchNetworkInfo(): NodeInfoResponse {
    val nodeInfoUri = "${this.lcdUrl}/node_info"
    val response = Network.get<Map<String, Any>>(nodeInfoUri)
        ?: throw Exception("Could not find node info")

    if (response["statusCode"] != 200 || response["body"] == null) {
        throw Exception("Could not find node info (status ${response["statusCode"]})")
    }

    val responseBody = response["body"] as Map<String, Any>
    val nodeInfoResponse: NodeInfoResponse =
        jacksonObjectMapper().convertValue(responseBody, NodeInfoResponse::class.java)
    return nodeInfoResponse
}

suspend fun NetworkInfo.isVersion(version: String): Boolean {
    require(version.isNotEmpty()) { "The version must not be empty. An valid example is '2.2'" }
    val nodeInfoResponse = this.fetchNetworkInfo()
    return nodeInfoResponse.applicationVersion.version.contains(version)
}

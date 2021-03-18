package network.commercio.sdk.networking

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Represents a response from the node_info endpoint from the blockchain.
 */
data class NodeInfoResponse(
    @JsonProperty("node_info") val nodeInfo: NodeInfo,
    @JsonProperty("application_version") val applicationVersion: NodeInfoApplicationVersion
)

data class NodeInfo(
    @JsonProperty("protocol_version")
    val valprotocolVersion: NodeInfoProtocolVersion,
    val id: String,
    @JsonProperty("listen_addr") val listenAddr: String,
    val network: String,
    val version: String,
    val channels: String,
    val moniker: String,
    val other: NodeInfoOther
)

data class NodeInfoProtocolVersion(
    val p2p: String? = null,
    val block: String? = null,
    val app: String? = null
)

data class NodeInfoOther(
    @JsonProperty("tx_index") val txIndex: String? = null,
    @JsonProperty("rpc_address") val rpcAddress: String? = null
)


data class NodeInfoApplicationVersion(
    val name: String? = null,
    @JsonProperty("server_name") val serverName: String? = null,
    @JsonProperty("client_name") val clientName: String? = null,
    val version: String,
    val commit: String? = null,
    @JsonProperty("build_tags") val buildTags: String? = null,
    val go: String? = null,
    @JsonProperty("build_deps") val buildDeps: List<String>? = null
)
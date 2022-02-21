package network.commercio.sdk.networking

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.Assert
import org.junit.Test

class NetworkInfoResponseTests {

    val nodeInfoApplicationVersion = NodeInfoApplicationVersion(
        name = "commercionetwork",
        serverName = "cnd",
        clientName = "cndcli",
        version = "2.2.0-pre.2-1-g3b3a9095",
        commit = "3b3a9095de6b65f260707b681cc52b998a3f23b3",
        buildTags = "netgo,ledger",
        go = "go version go1.16.2 linux/amd64",
        cosmosSdkVersion = "v0.45.0"
    )

    val nodeInfo = NodeInfo(
        protocolVersion = NodeInfoProtocolVersion(p2p = "7", block = "10", app = "0"),
        id = "c82af0f14d37958d93622a38eb48c74a238c0f55",
        listenAddr = "tcp://0.0.0.0:26656",
        network = "chain-oCG4N2",
        version = "0.33.9",
        channels = "4020212223303800",
        moniker = "node0",
        other = NodeInfoOther(
            txIndex = "on",
            rpcAddress = "tcp://0.0.0.0:26657"
        )
    )

    val nodeInfoResponse = NodeInfoResponse(
        nodeInfo = nodeInfo,
        applicationVersion = nodeInfoApplicationVersion
    )

    @Test
    fun `fromJson should behave correctly`() {

        val objSerialized = jacksonObjectMapper().writeValueAsString(nodeInfoResponse)
        val fullJson =
            """{"node_info":{"protocol_version":{"p2p":"7","block":"10","app":"0"},"id":"c82af0f14d37958d93622a38eb48c74a238c0f55","listen_addr":"tcp://0.0.0.0:26656","network":"chain-oCG4N2","version":"0.33.9","channels":"4020212223303800","moniker":"node0","other":{"tx_index":"on","rpc_address":"tcp://0.0.0.0:26657"}},"application_version":{"name":"commercionetwork","server_name":"cnd","client_name":"cndcli","version":"2.2.0-pre.2-1-g3b3a9095","commit":"3b3a9095de6b65f260707b681cc52b998a3f23b3","build_tags":"netgo,ledger","go":"go version go1.16.2 linux/amd64","build_deps":null,"cosmos_sdk_version":"v0.45.0"}}""".trimMargin()
        Assert.assertEquals(objSerialized, fullJson)
    }

    @Test
    fun `convertValue from map to NodeInfoResponse should behave correctly`() {

        val _protocol_version = mutableMapOf<String, Any>()
        _protocol_version["p2p"] = "7"
        _protocol_version["block"] = "10"
        _protocol_version["app"] = "0"

        val _other = mutableMapOf<String, String>()
        _other["tx_index"] = "on"
        _other["rpc_address"] = "tcp://0.0.0.0:26657"


        val _node_info = mutableMapOf<String, Any>()
        _node_info["protocol_version"] = _protocol_version
        _node_info["id"] = "c82af0f14d37958d93622a38eb48c74a238c0f55"
        _node_info["listen_addr"] = "tcp://0.0.0.0:26656"
        _node_info["network"] = "chain-oCG4N2"
        _node_info["version"] = "0.33.9"
        _node_info["channels"] = "4020212223303800"
        _node_info["moniker"] = "node0"
        _node_info["other"] = _other


        val _application_version = mutableMapOf<String, Any>()
        _application_version["name"] = "commercionetwork"
        _application_version["server_name"] = "cnd"
        _application_version["client_name"] = "cndcli"
        _application_version["version"] = "2.2.0-pre.2-1-g3b3a9095"
        _application_version["commit"] = "3b3a9095de6b65f260707b681cc52b998a3f23b3"
        _application_version["build_tags"] = "netgo,ledger"
        _application_version["go"] = "go version go1.16.2 linux/amd64"
        _application_version["cosmos_sdk_version"] = "v0.45.0"


        val json = mutableMapOf<String, Any>()
        json["node_info"] = _node_info
        json["application_version"] = _application_version

        Assert.assertEquals(
            nodeInfoResponse,
            jacksonObjectMapper().convertValue(json, NodeInfoResponse::class.java)
        )
    }
}
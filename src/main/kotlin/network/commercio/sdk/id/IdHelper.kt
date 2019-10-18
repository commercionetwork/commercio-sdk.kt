package network.commercio.sdk.id

import io.ktor.client.request.get
import network.commercio.sacco.Wallet
import network.commercio.sdk.entities.Did
import network.commercio.sdk.entities.id.DidDocument
import network.commercio.sdk.networking.Network
import network.commercio.sdk.utils.tryOrNull

object IdHelper {

    suspend fun getDidDocument(did: Did, wallet: Wallet): DidDocument? = tryOrNull {
        val identityEndpoint = "${wallet.networkInfo.lcdUrl}/identities/${did.value}"
        Network.client.get<DidDocument>(identityEndpoint)
    }

}
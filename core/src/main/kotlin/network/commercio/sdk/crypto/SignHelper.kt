package network.commercio.sdk.crypto

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import network.commercio.sacco.Wallet

object SignHelper {

    fun signSorted(data: Any, wallet: Wallet): ByteArray {
        // Convert the signature to a JSON and sort it
        val objectMapper = jacksonObjectMapper().apply {
            configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
            configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
        }

        val jsonSignData = objectMapper.writeValueAsString(data)
        return wallet.signTxData(jsonSignData)
    }

}
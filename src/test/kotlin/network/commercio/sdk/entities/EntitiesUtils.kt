package network.commercio.sdk.entities

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

internal val msgObjectMapper = jacksonObjectMapper().apply {
    configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
    configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true)
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}
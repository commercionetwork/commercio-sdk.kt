package network.commercio.sdk.entities.mint

import com.fasterxml.jackson.annotation.JsonProperty
import network.commercio.sacco.models.types.StdCoin
import java.util.*

data class ExchangeTradePosition(
    @JsonProperty("created_at") val createdAt: Date,
    val id: String,
    val owner: String,
    @JsonProperty("exchange_rate") val exchangeRate: String,
    val collateral: String,
    val credits: StdCoin
)
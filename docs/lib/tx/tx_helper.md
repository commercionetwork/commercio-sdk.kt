# Tx helper
Allows to easily perform common transaction operations.
## Provided operations
1. Creates a transaction having the given `msgs` and `fee` inside, signs it with the given `wallet` and sends it to the blockchain

```kotlin
suspend fun createSignAndSendTx(
    msgs: List<StdMsg>,
    fee: StdFee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "1000"))),
    wallet: Wallet
): TxResponse
```

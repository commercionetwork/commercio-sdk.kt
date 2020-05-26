# Tx helper

Allows to easily perform common transaction operations.

## Provided operations

1. Creates a transaction having the given `msgs`, signs it with the given `wallet` and sends it to the blockchain. 
 Optional parameters can be `fee` and broadcasting `mode`, that can be of type "sync", "async" or "block".
 
```kotlin
suspend fun createSignAndSendTx(
    msgs: List<StdMsg>,
    wallet: Wallet,
    fee: StdFee? = null,
    mode: BroadcastingMode? = null
): TxResponse
```



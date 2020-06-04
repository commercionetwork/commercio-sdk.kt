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

## Usage examples
```kotlin
val info = NetworkInfo(bech32Hrp = "did:com:", lcdUrl = "http://localhost:1317")

val mnemonic = listOf(
    "will",
    "hard",
    "topic",
    "spray",
    "beyond",
    "ostrich",
    "moral",
    "morning",
    "gas",
    "loyal",
    "couch",
    "horn",
    "boss",
    "across",
    "age",
    "post",
    "october",
    "blur",
    "piece",
    "wheel",
    "film",
    "notable",
    "word",
    "man"
)

val wallet = Wallet.derive(mnemonic = mnemonic, networkInfo = info)

val deposit = listOf(StdCoin(denom = "ucommercio", amount = "100"))

val msgDeposit = listOf(
    MsgSend(
        amount = deposit,
        fromAddress = wallet.bech32Address,
        toAddress = "did:com:14ttg3eyu88jda8udvxpwjl2pwxemh72w0grsau"
    )
)

val fee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "10000")))
val mode = TxHelper.BroadcastingMode.BLOCK

try {
    TxHelper.createSignAndSendTx(
        msgs = msgDeposit,
        wallet = wallet,
        fee = fee,
        mode = mode
    )
} catch (e: Exception){
    throw e
}
```

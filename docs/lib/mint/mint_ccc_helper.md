# Mint CCC Helper

`MintCccHelper` allows to easily create a `MintCcc` object.

## Provided Operations

1. Creates a `MintCcc` from the given `wallet`, a deposit `amount` and the `id`.

   N.B.: `amount` is a list, the `id` is a Version 4 UUID identifier.

    ```kotlin
    fun fromWallet(
        wallet: Wallet,
        amount: List<StdCoin>,
        id: String
    ): MintCcc
    ```

## Usage examples

```kotlin
val info = NetworkInfo(bech32Hrp = "did:com:", lcdUrl = "http://localhost:1317")

val userMnemonic = listOf(
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

val wallet = Wallet.derive(mnemonic = userMnemonic, networkInfo = info)
val amount = listOf(StdCoin(denom = "uccc", amount = "20"))

val mintCcc = MintCccHelper.fromWallet(
   amount = listOf(stdCoin),
   wallet = wallet,
   id = UUID.randomUUID().toString()
)
```

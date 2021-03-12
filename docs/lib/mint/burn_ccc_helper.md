# Burn CCC Helper

`BurnCccHelper` allows to easily create a new `BurnCcc` object.

## Provided Operations

1. Creates a `BurnCcc` from the given `wallet`, the `amount` to be burned and a MintCcc `id`.

   N.B.: `amount` is an object.

    ```kotlin
    fun fromWallet(
        wallet: Wallet,
        id: String,
        amount: StdCoin
    ): BurnCcc
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

val amount = StdCoin(denom = "uccc", amount = "10")
val id = "74fe4236-925b-4cdf-899b-0844de64eff3"

val burnCcc = BurnCccHelper.fromWallet(
   wallet = wallet,
   amount = amount,
   id = id
)
```
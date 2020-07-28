# Open Cdp Helper

Open Cdp Helper allows to easily create a OpenCdp object.

## Provided Operations

1. Creates an OpenCdp from the given `wallet` and deposit `amount`.

    ```kotlin
    fun fromWallet(
        wallet: Wallet,
        amount: List<StdCoin>
    ): OpenCdp
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

val userWallet = Wallet.derive(mnemonic = userMnemonic, networkInfo = info)

val amount = listOf(StdCoin(denom = "ucommercio", amount = "100"))
val openCdp = OpenCdpHelper.fromWallet(
    wallet = wallet, 
    amount = amount
)
```

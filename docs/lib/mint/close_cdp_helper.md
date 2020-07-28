# Close Cdp Helper

Close Cdp Helper allows to easily create a CloseCdp object.

## Provided Operations

1. Creates a CloseCdp from the given `wallet` and `timeStamp`. N.B.: `timeStamp` is the 'height' at which the position was opened

    ```kotlin
    fun fromWallet(
        timeStamp: Int,
        wallet: Wallet
    ): CloseCdp
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

val closeCdp2 = CloseCdpHelper.fromWallet(
    wallet = wallet,
    timeStamp = 508435 // to replace with height value 
)
```
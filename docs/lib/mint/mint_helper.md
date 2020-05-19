# Mint helper

Mint helper allows to easily perform all the operations related to the commercio.network `mint` module.

## Provided operations

1. Opens a new [CDP](../glossary.md) depositing the given `commercioTokenAmount`

    ```kotlin
    suspend fun openCdp(
        commercioTokenAmount: ULong,
        wallet: Wallet,
        fee: StdFee? = null
    ): TxResponse
    ```

2. Closes the CDP having the given `timestamp`. This will allow the user to trade back the lent amount of pico Commercio Cash Credits (`uccc`) to get the
   deposited amount of pico Commercio Tokens (`ucommercio`).

    ```kotlin
    suspend fun closeCdp(
        timestamp: Int,
        wallet: Wallet,
        fee: StdFee? = null
    ): TxResponse
    ```

## Usage examples

```kotlin
val info = NetworkInfo(bech32Hrp = "did:com:", lcdUrl = "http://localhost:1317")

val mnemonic = listOf("will", "hard", ..., "man")
val wallet = Wallet.derive(
  mnemonic = mnemonic,
  networkInfo = info
)

val amount = 100_000.toULong()

//Opening a cdp
MintHelper.openCdp(
  commercioTokenAmount = amount,
  wallet = wallet
)

//Closing a cdp
MintHelper.closeCdp(
  timestamp = timestamp,
  wallet = wallet
)

```

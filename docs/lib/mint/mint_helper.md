# Mint helper
Mint helper allows to easily perform all the operations related to the commercio.network `mint` module.
# Provided operations
1. Opens a new CDP depositing the given `commercioTokenAmount`. Optionally `fee` and broadcasting `mode` parameters can be specified.
    ```kotlin
    suspend fun openCdp(
        commercioTokenAmount: ULong,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse
    ```
2. Performs a transaction opening a new CDP `openCdp` as being associated with the address present inside the specified `wallet`. Optionally `fee` and broadcasting `mode` parameters can be specified.   
    ```kotlin
    suspend fun openCdp(
        openCdp: OpenCdp,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse
    ```
3. Closes the CDP having the given `timestamp`. Optionally `fee` and broadcasting `mode` parameters can be specified.
    ```kotlin
    suspend fun closeCdp(
        timestamp: Int,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse
    ```
   
4. Closes the open CDPs having the given `closeCdps` list as being associated with the address present inside the specified `wallet`. Optionally `fee` and broadcasting `mode` parameters can be specified.
    ```kotlin    
    suspend fun closeCdpsList(
        closeCdps: List<CloseCdp>,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse
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

val amount = 100000000.toULong()

try {
    //Opening a cdp
    MintHelper.openCdp(commercioTokenAmount = amount, wallet = userWallet)
    
    //Closing a cdp
    MintHelper.closeCdp(timestamp = timestamp, wallet = userWallet)
} catch (e: Exception){
    throw e
}
```
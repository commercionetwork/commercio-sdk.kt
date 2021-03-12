# Mint helper

Mint helper allows to easily perform all the operations related to the commercio.network `mint` module.

# Provided operations

1. Mints the CCCs having the given `mintCccs` list as being associated with the address present inside the
   specified `wallet`. Optionally `fee` and broadcasting `mode` parameters can be specified.
    ```kotlin
    suspend fun mintCccsList(
        mintCccs: List<MintCcc>, 
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
   ): TxResponse
    ```

2. Returns the list of all the `ExchangeTradePosition` that the specified wallet has minted.
    ```kotlin    
    suspend fun getExchangeTradePositions(
        wallet: Wallet
    ): List<ExchangeTradePosition>
    ```

3. Burns the CCCs having the given `burnCccs` list as being associated with the address present inside the
   specified `wallet`. Optionally `fee` and broadcasting `mode` parameters can be specified.
    ```kotlin    
    suspend fun burnCccsList(
        burnCccs: List<BurnCcc>,
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

val wallet = Wallet.derive(mnemonic = userMnemonic, networkInfo = info)
val stdCoin = StdCoin(denom = "uccc", amount = "20")
val fee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "10000")))// optional
val mode = TxHelper.BroadcastingMode.BLOCK // optional

try {

    val mintCcc = MintCcc(
        depositAmount = listOf(stdCoin),
        depositorDid = wallet.bech32Address,
        id = UUID.randomUUID().toString()
    )

    //Mint CCC
    MintHelper.mintCccsList(
        mintCccs = listOf(mintCcc),
        wallet = wallet,
        fee = fee,  // optional
        mode = mode  // optional
    )

    // Get all Exchange Trade Positions
    val etps = MintHelper.getExchangeTradePositions(wallet)

    val burnCccs = mutableListOf<BurnCcc>()

    etps.forEach {
        val _burnCcc = BurnCccHelper.fromWallet(
            wallet = wallet, id = it.id,
            amount = StdCoin(denom = it.credits.dnom, amount = it.credits.amount)
        )
        burnCccs.add(_burnCcc)
    }

    //Burn CCC
    MintHelper.burnCccsList(burnCccs, wallet)
} catch (e: Exception) {
    throw e
}
```
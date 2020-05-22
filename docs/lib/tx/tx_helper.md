# Tx helper

Allows to easily perform common transaction operations.

## Provided operations

1. Creates a transaction having the given `msgs` and `fee` inside, signs it with the given `wallet` and sends it to the blockchain

    ```kotlin
    suspend fun createSignAndSendTx(
        msgs: List<StdMsg>,
        wallet: Wallet,
        fee: StdFee?
    ): TxResponse
    ```

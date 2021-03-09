# RewardPoolDepositHelper

Allows to easily create a `DepositRewardPool` object.

## Provided Operations

1. Creates a `RewardPoolDeposit` from the given `wallet`, and deposit `amount`.

   N.B.: `amount` is a list.

    ```kotlin
    fun fromWallet(
        wallet: Wallet,
        amount: List<StdCoin>
    ): RewardPoolDeposit
    ```

## Usage examples

   ```kotlin
    val info = NetworkInfo(
   bech32Hrp = "did:com:",
   lcdUrl = "http://localhost:1317"
)

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
val depositAmount = listOf(StdCoin(denom = "ucommercio", amount = "10")) // only ucommercio

val rewardPoolDeposit = RewardPoolDepositHelper.fromWallet(
   wallet = wallet,
   amount = depositAmount
)
```

# Kyc helper

Kyc helper allows to easily perform all the operations related to the commercio.network `kyc` module.

## Provided operations

1. Sends a new transaction in order to invite the given `inviteUsers` users list and `wallet`. Optionally `fee` and
   broadcasting `mode` parameters can be specified.
    ```kotlin
    suspend fun inviteUsersList(
        inviteUsers: List<InviteUser>,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse
    ```

2. Buys the membership with the given `buyMemberships` memberships list and `wallet`. Optionally `fee` and
   broadcasting `mode` parameters can be specified.
    ```kotlin
    suspend fun buyMembershipsList(
        buyMemberships: List<BuyMembership>,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse
    ```

2. Deposit a list of the given deposits `rewardPoolDeposits` into reward pool with the depositor `wallet`.
   Optionally `fee` and broadcasting `mode` parameters can be specified.
   ```kotlin
   suspend fun rewardPoolDepositsList(
       rewardPoolDeposits: List<RewardPoolDeposit>,
       wallet: Wallet,
       fee: StdFee? = null,
       mode: BroadcastingMode? = null
   ): TxResponse
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
val government = Wallet.derive(mnemonic = userMnemonic, networkInfo = info)

val newUserMnemonic = listOf(
            "often",
            "emerge",
            "table",
            "boat",
            "add",
            "crowd",
            "obtain",
            "creek",
            "skill",
            "flat",
            "master",
            "gift",
            "provide",
            "peasant",
            "famous",
            "blur",
            "flight",
            "lady",
            "elephant",
   "twenty",
   "join",
   "depth",
   "laptop",
   "arrest"
)
val newUserWallet = Wallet.derive(newUserMnemonic, info)

try {
   // Invite user
   val responseInviteUser = KycHelper.inviteUsersList(
      wallet = government,
      inviteUsers = listOf(
         InviteUserHelper.fromWallet(
            wallet = government, // user with membership
            recipientDid = Did(newUserWallet.bech32Address) // user to invite
         )
      )
   )

   // Recharge the user
   val responseSendCoin = TxHelper.createSignAndSendTx(
      msgs = listOf(
         MsgSend(
            amount = listOf(
               StdCoin(denom = "uccc", amount = "100"),
               StdCoin(denom = "ucommercio", amount = "20000")
            ),
            fromAddress = government.bech32Address,
            toAddress = newUserWallet.bech32Address
         )
      ),
      wallet = government
   )

   // Buy a membership
   val responseBuy = KycHelper.buyMembershipsList(
      wallet = government,
      buyMemberships = listOf(
         BuyMembership(
            buyerDid = newUserWallet.bech32Address,
            membershipType = MembershipType.BRONZE,
            tsp = government.bech32Address
         )
      )
   )

   // Deposit into the reward pool
   val responseRewardPoolDeposit = rewardPoolDepositsList(
      wallet = wallet,
      rewardPoolDeposits = listOf(
         RewardPoolDepositHelper.fromWallet(
            wallet = wallet,
            amount = listOf(StdCoin(denom = "ucommercio", amount = "50"))
         )
      )
   )

} catch (e: Exception) {
   throw e
}
```
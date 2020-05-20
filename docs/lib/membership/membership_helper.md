# Membership helper

Membership helper allows to easily perform all the operations related to the commercio.network `membership` module.

## Provided operations

1. Sends a new transaction in order to invite the given `user`.

    ```kotlin
    suspend fun inviteUser(
        user: Did,
        wallet: Wallet,
        fee: StdFee? = null
    ): TxResponse
    ```

2. Buys the membership with the given `membershipType`.

    ```kotlin
    suspend fun buyMembership(
        membershipType: MembershipType,
        wallet: Wallet,
        fee: StdFee? = null
    ): TxResponse
    ```

## Usage examples

```kotlin
val info = NetworkInfo(
    bech32Hrp = "did:com:",
    lcdUrl = "http://localhost:1317"
)

val mnemonic = listOf("will", "hard", ..., "man")
val wallet = Wallet.derive(
  mnemonic = mnemonic,
  networkInfo = info
)

val newUserMnemonic = listOf("often", "emerge", ..., "arrest")
val newUserWallet = Wallet.derive(
  mnemonic = newUserMnemonic,
  networkInfo = info
)

// Invite user
MembershipHelper.inviteUser(
    user = Did(newUserWallet.bech32Address),
    wallet = wallet
)

// Buy a membership
MembershipHelper.buyMembership(
    membershipType = MembershipType.GOLD,
    wallet = newUserWallet
)
```

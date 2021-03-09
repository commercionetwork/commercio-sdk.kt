# Membership helper
Membership helper allows to easily perform all the operations related to the commercio.network `membership` module.
## Provided operations
1. Sends a new transaction in order to invite the given `userDid`. Optionally `fee` and broadcasting `mode` parameters can be specified.
    ```kotlin
    suspend fun inviteUser( 
        user: Did,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse
    ```
2. Sends a new transaction in order to invite the given `inviteUsers` users list. Optionally `fee` and broadcasting `mode` parameters can be specified.   
    ```kotlin
    suspend fun inviteUsersList(
        inviteUsers: List<InviteUser>,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse
    ```
3. Buys the membership with the given `membershipType`. Optionally `fee` and broadcasting `mode` parameters can be specified.
    ```kotlin
    suspend fun buyMembership(
        membershipType: MembershipType,
        wallet: Wallet,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse
    ```
4. Buys the membership with the given `buyMemberships` memberships list. Optionally `fee` and broadcasting `mode` parameters can be specified.   
    ```kotlin
    suspend fun buyMembershipsList(
        buyMemberships: List<BuyMembership>,
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
    
    val userWallet = Wallet.derive(mnemonic = userMnemonic, networkInfo = info)
    
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
            inviteUser(
                user = Did(newUserWallet.bech32Address), 
                wallet = userWallet
            )
            
            // Buy a membership
            buyMembership(
                membershipType = MembershipType.GOLD, 
                wallet = newUserWallet
            )
        } catch (e: Exception){
            throw e
        }
```
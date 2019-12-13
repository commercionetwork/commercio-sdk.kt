# Membership helper
Membership helper allows to easily perform all the operations related to the commercio.network `membership` module.
## Provided operations
1. Sends a new transaction in order to invite the given `user`.
```kotlin
suspend fun inviteUser(user: Did, wallet: Wallet): TxResponse
```
2. Buys the membership with the given `membershipType`.
```kotlin
suspend fun buyMembership(membershipType: MembershipType, wallet: Wallet)
    : TxResponse
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
```

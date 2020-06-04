# Buy Membership Helper

Buy Membership Helper allows to easily create a Buy Membership object.

## Provided Operations

1. Creates a BuyMembership from the given `wallet` and `membershipType`.

    ```kotlin
    fun fromWallet(
        wallet: Wallet,
        membershipType: MembershipType
    ): BuyMembership
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
        
    val buyMembership = BuyMembershipHelper.fromWallet(
        wallet = wallet,
        membershipType = MembershipType.GOLD
    )
```
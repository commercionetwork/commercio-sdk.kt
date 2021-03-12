# Invite User Helper

Invite User Helper allows to easily create an InviteUser object.

## Provided Operations

1. Creates an InviteUser from the given `wallet` and `recipientDid`.

    ```kotlin
    fun fromWallet(
        wallet: Wallet,
        recipientDid: Did
    ): InviteUser
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
        
    val inviteUser = InviteUserHelper.fromWallet(
        wallet = wallet,
        recipientDid = "did:com:1crltcqwt9r0tpy9v0jnuu7lw6snl5k2wez8cca"
    )
```
# Did Document Helper
Did Document Helper allows to perform common Did Document related operations.


## Provided Operations
1. Creates a [DidDocument](../glossary.md) from the given `wallet` and the `pubKeys`. 
Optionally the list `service` can be specified.
```kotlin
fun fromWallet(
        wallet: Wallet,
        pubKeys: List<PublicKeyWrapper> = listOf(),
        service: List<DidDocumentService>? = null
    ): DidDocument
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
    
    // --- Generate keys
    val rsaVerificationKeyPair = KeysHelper.generateRsaKeyPair()
    val rsaSignatureKeyPair = KeysHelper.generateRsaKeyPair(type = "RsaSignatureKey2018")

    // --- Create Did Document
    val didDocument = DidDocumentHelper.fromWallet(
        wallet,
        listOf(rsaVerificationKeyPair.publicWrapper, rsaSignatureKeyPair.publicWrapper)
    )
```
# Did Document Helper
Did Document Helper allows to perform common Did Document related operations.


## Provided Operations
1. Creates a [DidDocument](../glossary.md) from the given `wallet` and optional `pubKeys`.
```kotlin
fun fromWallet(wallet: Wallet, pubKeys: List<PublicKey> = listOf()): DidDocument
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
    
    val rsaKeyPair = KeysHelper.generateRsaKeyPair()
    val ecKeyPair = KeysHelper.generateEcKeyPair()
    val didDocument = DidDocumentHelper.fromWallet(wallet, listOf(rsaKeyPair.public, ecKeyPair.public))
```

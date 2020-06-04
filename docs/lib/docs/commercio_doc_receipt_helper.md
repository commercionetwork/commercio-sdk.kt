# Commercio Doc Receipt Helper

Allows to easily create a CommercioDocReceipt and perform common related operations.

## Provide Operations

1. Creates a CommercioDoc from the given `wallet`, `recipient`, `txHash`, `documentId` and optionally `proof`.

    ```kotlin
    fun fromWallet(
        wallet: Wallet,
        recipient: Did,
        txHash: String,
        documentId: String,
        proof: String = ""
    ): CommercioDocReceipt
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
val senderWallet = Wallet.derive(mnemonic = userMnemonic, networkInfo = info)
val recipientMnemonic = listOf(
            "crisp",
            "become",
            "thumb",
            "fetch",
            "forest",
            "senior",
            "polar",
            "slush",
            "wise",
            "wash",
            "doctor",
            "sunset",
            "skate",
            "disease",
            "power",
            "tool",
            "sock",
            "upper",
            "diary",
            "what",
            "trap",
            "artist",
            "wood",
            "cereal"
)
val recipientWallet = Wallet.derive(recipientMnemonic, info)

val recipientDid = Did(senderWallet.bech32Address)
val id1 = UUID.randomUUID().toString()

val commercioDoc = CommercioDocHelper.fromWallet(
    id = id1,
    metadata = metadata,
    recipients = listOf(Did(recipientWallet.bech32Address)),
    wallet = senderWallet,
    checksum = checksum,
    aesKey = KeysHelper.generateAesKey(),
    encryptedData = listOf(EncryptedData.METADATA_CONTENT_URI),
    contentUri = "https://example.com/document"
)

val response = DocsHelper.shareDocumentsList(
    commercioDocs = listOf(commercioDoc),
    wallet = senderWallet
)

val txHash = response.txHash
       
val commercioDocReceipt = CommercioDocReceiptHelper.fromWallet(
    wallet = recipientWallet,
    recipient = Did(senderWallet.bech32Address),
    txHash = txHash,
    documentId = id1,
    proof = ""
)
```


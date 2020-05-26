# Docs helper
Docs helper allows to easily perform all the operations related to the commercio.network `docs` module.

## Provided operations

1. Creates a new transaction that allows to share the document associated with the given `metadata` and having the optional fields `contentUri`, `doSign`, `checksum`, `fee` and broadcasting `mode`.
      If `encryptedData` is specified, encrypts the proper data and optional `aesKey` for the specified `recipients` and then sends the transaction to the blockchain.
      
```kotlin
suspend fun shareDocument(
     id: String,
     metadata: CommercioDoc.Metadata,
     recipients: List<Did>,
     wallet: Wallet,
     doSign: CommercioDoc.CommercioDoSign? = null,
     checksum: CommercioDoc.Checksum? = null,
     aesKey: SecretKey = KeysHelper.generateAesKey(),
     encryptedData: List<EncryptedData> = listOf(),
     fee: StdFee? = null,
     contentUri: String = "",
     mode: BroadcastingMode? = null
): TxResponse
```
2. Returns the list of all the `CommercioDoc` that the specified `address` has sent.
```kotlin
suspend fun getSentDocuments(address: Did, wallet: Wallet): List<CommercioDoc>
```
3. Returns the list of all the `CommercioDoc` that the specified `address` has received.
```kotlin
suspend fun getReceivedDocuments(address: Did, wallet: Wallet): List<CommercioDoc>
```
4. Creates a new transaction which tells the `recipient` that the document having the specified `documentId` and
   present inside the transaction with hash `txHash` has been properly seen; optionally `proof` of reading, `fee` and broadcasting `mode`.
```kotlin
suspend fun sendDocumentReceipt(
    recipient: Did,
    txHash: String,
    documentId: String,
    proof: String = "",
    wallet: Wallet,
    fee: StdFee? = null,
    mode: BroadcastingMode? = null
): TxResponse
```
5. Returns the list of all the `CommercioDocReceipt` that have been sent from the given `address`.
```kotlin
suspend fun getSentReceipts(address: Did, wallet: Wallet)
    : List<CommercioDocReceipt>
```
6. Returns the list of all the `CommercioDocReceipt` that have been received from the given `address`
```kotlin
suspend fun getReceivedReceipts(address: Did, wallet: Wallet)
    : List<CommercioDocReceipt>
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

    // --- Share a document
    val docRecipientDid = Did(recipientWallet.bech32Address)
    
    val docId = UUID.randomUUID().toString()
    
    val response = DocsHelper.shareDocument(
        id = docId,
        contentUri = "https://example.com/document",
        metadata = CommercioDoc.Metadata(
            contentUri = "https://example.com/document/metadata",
            schema = CommercioDoc.Metadata.Schema(
                uri = "https://example.com/custom/metadata/schema",
                version = "1.0.0"
            )
        ),
        recipients = listOf(recipientDid),
        fee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "10000"))),
        wallet = senderWallet
    )
    
    val txHash = (response as TxResponse.Successful).txHash
    
    
    DocsHelper.sendDocumentReceipt(
        recipient = recipientDid,
        txHash = txHash,
        documentId = docId,
        wallet = recipientWallet
    )
```

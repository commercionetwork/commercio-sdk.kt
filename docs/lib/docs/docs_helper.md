# Docs helper
Docs helper allows to easily perform all the operations related to the commercio.network `docs` module.

## Provided operations

1. Creates a new transaction that allows to share the document associated with the given `contentUri` and
   having the given `metadata` and `checksum`.   
   If `encryptedData` is specified, encrypts the proper data for the specified `recipients` and then sends the transaction to the blockchain.
```kotlin
@JvmOverloads
    suspend fun shareDocument(
        id: String,
        contentUri: String,
        metadata: CommercioDoc.Metadata,
        recipients: List<Did>,
        fees: List<StdCoin>,
        wallet: Wallet,
        checksum: CommercioDoc.Checksum? = null,
        aesKey: SecretKey = KeysHelper.generateAesKey(),
        encryptedData: List<EncryptedData> = listOf()
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
   present inside the transaction with hash `txHash` has been properly seen.
```kotlin
 @JvmOverloads
    suspend fun sendDocumentReceipt(
        recipient: Did,
        txHash: String,
        documentId: String,
        proof: String = "",
        wallet: Wallet
    ): TxResponse
```
5. Returns the list of all the `CommercioDocReceipt` that have been sent from the given `address`.
```kotlin
suspend fun getSentReceipts(address: Did, wallet: Wallet): List<CommercioDocReceipt>
```
6. Returns the list of all the `CommercioDocReceipt` that have been received from the given `address`
```kotlin
suspend fun getReceivedReceipts(address: Did, wallet: Wallet): List<CommercioDocReceipt>
```

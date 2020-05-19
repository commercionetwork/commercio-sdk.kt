# Docs helper

Docs helper allows to easily perform all the operations related to the commercio.network `docs` module.

## Provided operations

1. Creates a new transaction that allows to share the document associated with the given `contentUri` and having the given `metadata` and `checksum`.

   If `encryptedData` is specified, encrypts the proper data for the specified `recipients` and then sends the transaction to the blockchain.

    ```kotlin
    suspend fun shareDocument(
        id: String,
        contentUri: String ="",
        metadata: CommercioDoc.Metadata,
        recipients: List<Did>,
        wallet: Wallet,
        checksum: CommercioDoc.Checksum? = null,
        aesKey: SecretKey = KeysHelper.generateAesKey(),
        encryptedData: List<EncryptedData> = listOf(),
        doSign: CommercioDoc.CommercioDoSign? = null,
        fee: StdFee? = null
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
    suspend fun sendDocumentReceipt(
        recipient: Did,
        txHash: String,
        documentId: String,
        proof: String = "",
        wallet: Wallet,
        fee: StdFee? = null
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

    val senderMnemonic = listOf("will", "hard", ..., "man")
    val senderWallet = Wallet.derive(mnemonic = senderMnemonic, networkInfo = info)

    val recipientMnemonic = listOf("crisp", "become", ..., "cereal")
    val recipientWallet = Wallet.derive(recipientMnemonic, info)
    val docRecipientDid = Did(recipientWallet.bech32Address)

    // --- Share a document
    val docId = UUID.randomUUID().toString()

    val checksum = CommercioDoc.Checksum(
      value = "bd29066606c7496d3e0bae11b1c7a3557ed0881535673e81c9a3ed4f",
      algorithm = CommercioDoc.Checksum.Algorithm.SHA256
    )

    val doSign = CommercioDoc.CommercioDoSign(
      storageUri= "http://www.commercio.network",
      signerIstance= "did:com:1cc65t29yuwuc32ep2h9uqhnwrregfq230lf2rj",
      sdnData= listOf(
      CommercioDoc.CommercioDoSign.CommercioSdnData.COMMON_NAME,
      CommercioDoc.CommercioDoSign.CommercioSdnData.SURNAME
      ),
      vcrId= "xxxxx",
      certificateProfile= "xxxxx"
    )

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
        recipients = listOf(docRecipientDid),
        fee = StdFee(
          gas = "200000",
          amount = listOf(
            StdCoin(denom = "ucommercio", amount = "10000")
          )
        ),
        wallet = senderWallet,
        checksum = checksum,
        doSign = doSign
    )

    val txHash = (response as TxResponse.Successful).txHash

    DocsHelper.sendDocumentReceipt(
        recipient = Did(senderWallet.bech32Address),
        txHash = txHash,
        documentId = docId,
        wallet = recipientWallet,
        fee = StdFee(
          gas = "200000",
          amount = listOf(
            StdCoin(denom = "ucommercio", amount = "10000")
          )
        )
    )
```

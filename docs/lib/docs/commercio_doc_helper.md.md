# Commercio Doc Helper

Commercio Doc Helper allows to easily create a Commercio Doc.

## Provide Operations

1. Creates a `CommercioDoc` from the given `wallet`, the list of recipients `recipients`, an unique document `id` (UUID
   v4 format) and document `metadata`. Optionally `contentUri`, `checksum`, `doSign`, `encryptedData`, `aesKey` can be
   provided. If `doSign` is provided then also the `checksum` field is required.

    ```kotlin
    suspend fun fromWallet(
        id: String,
        metadata: CommercioDoc.Metadata,
        recipients: List<Did>,
        wallet: Wallet,
        doSign: CommercioDoc.CommercioDoSign? = null,
        checksum: CommercioDoc.Checksum? = null,
        aesKey: SecretKey = KeysHelper.generateAesKey(),
        encryptedData: List<EncryptedData> = listOf(),
        contentUri: String = ""
    ): CommercioDoc
    ```

## Usage examples

```kotlin
   // Configure the blockchain network
   val info = NetworkInfo(
       bech32Hrp = "did:com:",
       lcdUrl = "http://localhost:1317"
   )

   // Derive the sender wallet from her mnemonics
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
   
   // Get the recipient(s) wallet address
   val docRecipientDid = Did("did:com:14ttg3eyu88jda8udvxpwjl2pwxemh72w0grsau")
   
   // Generate a random UUID (or use a meaningful one)
   val docId = UUID.randomUUID().toString()
   
   // URI location of the shared document
   val contentUri = "https://example.com/document"
   
   // Let's assume that we want to encrypt the CONTENT_URI field to hide the document location in the resulting transaction
   val encryptedData = listOf(EncryptedData.CONTENT_URI)
   
   // Let's assume that we have computed the document checksum in SHA256
   val checksum = CommercioDoc.Checksum(
       value = "a00ab326fc8a3dd93ec84f7e7773ac2499b381c4833e53110107f21c3b90509c",
       algorithm = CommercioDoc.Checksum.Algorithm.SHA256
   )
   
   // Let's assume that we want to digitally sign
   val doSign = CommercioDoc.CommercioDoSign(
       storageUri = "http://www.commercio.network",
       signerIstance = "did:com:1cc65t29yuwuc32ep2h9uqhnwrregfq230lf2rj",
       sdnData = listOf(
           CommercioDoc.CommercioDoSign.CommercioSdnData.COMMON_NAME,
           CommercioDoc.CommercioDoSign.CommercioSdnData.SURNAME
       ),
       vcrId = "xxxxx",
       certificateProfile = "xxxxx"
   )
   
   val metadata = CommercioDoc.Metadata(
       contentUri = "https://example.com/document/metadata",
       schema = CommercioDoc.Metadata.Schema(
           uri = "https://example.com/custom/metadata/schema",
           version = "1.0.0"
       )
   )
   
   try {
       val commercioDoc = CommercioDocHelper.fromWallet(
           id = docId,
           recipients = listOf(docRecipientDid),
           wallet = senderWallet,
           doSign = doSign,
           checksum = checksum,
           encryptedData = encryptedData,
           contentUri = "https://example.com/document",
           metadata = metadata
       )
   
       // Send the derived CommercioDoc to the blockchain
       val txResult = DocsHelper.shareDocumentsList(
           listOf(commercioDoc),
           senderWallet
       )
   
   } catch (e: Exception) {
       throw e
   }
```


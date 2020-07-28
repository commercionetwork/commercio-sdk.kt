# Commercio Doc Helper

Commercio Doc Helper allows to easily create a Commercio Doc.

## Provide Operations

1. Creates a `CommercioDoc` from the given `wallet`, `recipients`, `id`, `metadata` and optionally `contentUri`, `checksum`, `doSign`, `encryptedData`, `aesKey`.

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


    val docId = UUID.randomUUID().toString()
    val mode = TxHelper.BroadcastingMode.BLOCK 
    val fee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "10000"))) 


    val checksum = CommercioDoc.Checksum(
        value = "bd29066606c7496d3e0bae11b1c7a3557ed0881535673e81c9a3ed4f",
        algorithm = CommercioDoc.Checksum.Algorithm.SHA224
    )

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
            metadata = metadata,
            recipients = listOf(docRecipientDid),
            wallet = senderWallet,
            doSign = doSign,
            checksum = checksum,
            aesKey = KeysHelper.generateAesKey(),
            encryptedData = listOf(EncryptedData.METADATA_CONTENT_URI),
            contentUri = "https://example.com/document"
        )
    } catch (e: Exception){
        throw e
    }
```


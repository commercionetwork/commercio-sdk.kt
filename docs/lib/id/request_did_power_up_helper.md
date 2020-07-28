# Request Did PowerUp Helper

Request Did PowerUp Helper allows to easily create a Did PowerUp request.

## Provided Operations

1. Creates a RequestDidPowerUpHelper from the given `wallet`, `pairwiseDid`, `amount` and `privateKey`.
```kotlin
suspend fun fromWallet(
    wallet: Wallet,
    pairwiseDid: Did,
    amount: List<StdCoin>,
    privateKey: RSAPrivateKey
): RequestDidPowerUp
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
    val pairwiseWallet = Wallet.derive(mnemonic = userMnemonic, networkInfo = info, lastDerivationPathSegment = 1)
    val amount = StdCoin(denom = "ucommercio", amount = "100")

    val privateKeyPem = 
    """
    -----BEGIN PRIVATE KEY-----
    MIIEvQ...
    -----END PRIVATE KEY-----
    """

    val privateKeyPem = RSAKeyParser.parseRSAPrivateKeyFromPem(privateKeyPem)
    try {
        RequestDidPowerUpHelper.fromWallet(
            wallet = wallet,
            pairwiseDid = Did(pairwiseWallet.bech32Address),
            privateKey = privateKeyPem,
            amount = listOf(amount)
        )
    } catch (e: Exception){
        throw e
    }
```

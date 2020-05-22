# Did Document Helper

Did Document Helper allows to perform common Did Document related operations.

## Provided Operations

1. Creates a [DidDocument](../glossary.md) from the given `wallet` and optional `pubKeys`.

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

val mnemonic = listOf("will", "hard", ..., "man")
val wallet = Wallet.derive(mnemonic = mnemonic, networkInfo = info)

val rsaVerificationKeyPair = KeysHelper.generateRsaKeyPair()
val rsaSignatureKeyPair = KeysHelper.generateRsaKeyPair(
  type = "RsaSignatureKey2018"
)

// Create did document
val didDocument = DidDocumentHelper.fromWallet(
  wallet,
  listOf(
    rsaVerificationKeyPair.publicWrapper,
    rsaSignatureKeyPair.publicWrapper
  )
)
```

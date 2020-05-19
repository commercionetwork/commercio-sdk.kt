# Certificate Helper

Certificate helper allows to easily create X509 certificate from user's wallet.

## Provided operations

1. Creates an X509 certificate using the given `keyPair` and `walletAddress`.

    ```kotlin
    fun x509certificateFromWallet(
      walletAddress: String, keyPair: KeyPair, digestAlgorithm: String = "SHA256withRSA"
    ): X509Certificate
    ```

2. Return the PEM representation of the given `certificate`

    ```kotlin
    fun getPem(certificate: X509Certificate): String
    ```

## Usage examples

```kotlin
  val info = NetworkInfo(
    bech32Hrp = "did:com:",
    lcdUrl = "http://localhost:1317"
  )

  val userMnemonic = listOf("will", "hard", ..., "man")
  val userWallet = Wallet.derive(
    mnemonic = userMnemonic,
    networkInfo = info
  )

  val rsaKeyPair = KeysHelper.generateRsaKeyPair()

  // Creating X509 self-signed certificate
  val certificate = CertificateHelper.x509certificateFromWallet(
    userWallet.bech32Address, rsaKeyPair
  )
  
  // Getting the PEM encoded certificate
  val pemCertificate = CertificateHelper.getPem(certificate)
```

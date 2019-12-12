# Certificate Helper
Certificate helper allows to easily create X509 certificate from user's wallet.

## Provided operations
1. Creates an X509 certificate using the given `keyPair` and `walletAddress`.
```kotlin
fun x509certificateFromWallet(walletAddress: String, keyPair: KeyPair, digestAlgorithm: String = "SHA256withRSA"): X509Certificate
```
2. Return the PEM representation of the given `certificate`
```kotlin
fun getPem(certificate: X509Certificate): String
```

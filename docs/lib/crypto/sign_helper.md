# Sign helper
Sign helper allows to easily perform signature-related operations.

## Provided operations
Below you can find the sign helper's provided operations with some examples

1. Takes the given `data`, converts it to an alphabetically sorted JSON object and signs its content
   using the given `wallet`.
```kotlin
fun signSorted(data: Any, wallet: Wallet): ByteArray 
```
2. Verifies that the given `signature` is valid for the given `signedData` by using the public keys
   contained inside the given `didDocument`.
```kotlin
fun verifySignature(signature: ByteArray, signedData: Any, didDocument: DidDocument): Boolean
```
3. Sign `data` with the given `privateKey` and `digestAlgorithm`.  
Algorithm can be either `SHA256withRSA`, `SHA1withECDSA`.
```kotlin
fun signData(data: String, privateKey: PrivateKey, digestAlgorithm: String): String
```
4. Verify the `signedData` with the given `publicKey` and `digestAlgorithm`.  
Algorithm can be either `SHA256withRSA`, `SHA1withECDSA`.
```kotlin
fun verifySignedData(data: String, signedData: String, publicKey: PublicKey, digestAlgorithm: String): Boolean
```

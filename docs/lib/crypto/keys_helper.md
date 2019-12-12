# Keys helper
Keys helper allows to easily generate new RSA or AES keys.

## Provided operations
Below you can find the keys helper's provided operations with some examples

1. Generates a new random AES-256 secret key without any initializing vector.
```kotlin
fun generateAesKey(): SecretKey
```
2. Generates a secret aes key from the given `bytes`.
```kotlin
fun recoverAesKey(bytes: ByteArray): SecretKey
```
3. Generates a new RSA key pair having the given `bytes` length.  
   If no length is specified, the default is going to be 2048.
```kotlin
fun generateRsaKeyPair(bytes: Int = 2048): KeyPair
```
4. Generates a new EC key pair of type `secp256k1`
```kotlin
fun generateEcKeyPair(): KeyPair
```

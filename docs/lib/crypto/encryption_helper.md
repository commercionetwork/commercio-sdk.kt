# Encryption helper
Encryption helper allows to perform common encryption operations such as RSA/AES encryption and decryption.

## Provided operations
Below you can find the encryption helper's provided operations with some examples

1. Encrypts the given `data` with AES using the specified `key`.
```kotlin
fun encryptWithAes(data: String, key: SecretKey): ByteArray
```
2. Encrypts the given `data` with AES using the specified `key`.
```kotlin
fun encryptWithAes(data: ByteArray, key: SecretKey): ByteArray
```
3. Decrypts the given `data` with AES using the specified `key`.
```kotlin
fun decryptWithAes(data: ByteArray, key: SecretKey): ByteArray 
```
4. Encrypts the given `data` with RSA and the specified `key`.
```kotlin
fun encryptWithRsa(data: String, key: PublicKey): ByteArray
```
5. Encrypts the given data with RSA using the specified `key`.
```kotlin
fun encryptWithRsa(data: ByteArray, key: PublicKey): ByteArray
```
6. Encrypts the given `data` with RSA using the specified `certificate`.
```kotlin
fun encryptWithRsa(data: String, certificate: X509Certificate): ByteArray
```
7. Encrypts the given `data` with RSA using the specified `certificate`.
```kotlin
fun encryptWithRsa(data: ByteArray, certificate: X509Certificate): ByteArray
```
8. Decrypts the given data using the specified private `key`.
```kotlin
fun decryptWithRsa(data: ByteArray, key: PrivateKey): ByteArray
```

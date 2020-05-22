# Keys helper

Keys helper allows to easily generate new RSA or AES keys.

## Provided operations

Below you can find the keys helper's provided operations with some examples

1. Generates a nonce.

   ```kotlin
   fun generateNonce(): ByteArray
   ```

2. Generates a new random AES-256 secret key without any initializing vector.

    ```kotlin
    fun generateAesKey(): SecretKey
    ```

3. Generates a new random AES secret key with initializing vector.

    ```kotlin
    fun generateAesKey(bytes: Int = 256): SecretKey
    ```

4. Generates a secret AES key from the given `bytes`.

    ```kotlin
    fun recoverAesKey(bytes: ByteArray): SecretKey
    ```

5. Generates a new RSA key pair having the given `bytes` length and key `type`.  
   If no length is specified, the default is going to be 2048.
   If no type is specified, the default is going to be "RsaVerificationKey2018".

    ```kotlin
    fun generateRsaKeyPair(bytes: Int = 2048, type: String="RsaVerificationKey2018"): KeyPairWrapper
    ```

6. Generates a new EC key pair of type `secp256k1`

    ```kotlin
    fun generateEcKeyPair(type: String="EcdsaSecp256k1VerificationKey2019"): KeyPairWrapper
    ```

7. Export public `key` of `type` RSA or EC into an HEX string.

    ```kotlin
    fun exportPublicKeyHEX(key: PublicKey, type: String): String
    ```

8. Export private `key` of `type` RSA or EC into an HEX string.

    ```kotlin
    fun exportPrivateKeyHEX(key: PrivateKey, type: String): String
    ```

9. Sign `data` with the given `privateKey` and `digestAlgorithm`.
   Algorithm can be either SHA256withRSA, SHA1withECDSA.

   ```kotlin
   fun signData(data: String, privateKey: PrivateKey, digestAlgorithm: String): String
   ```

10. Verify the `signedData` with the given `publicKey` and `digestAlgorithm`.
    Algorithm can be either SHA256withRSA, SHA1withECDSA.

    ```kotlin
    fun verifySignedData(data: String, signedData: String, publicKey: PublicKey, digestAlgorithm: String): Boolean
    ```

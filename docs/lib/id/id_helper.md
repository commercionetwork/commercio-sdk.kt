# Id helper

Id helper allows to easily perform all the operations related to the commercio.network `id` module.

## Provided operations

1. Returns the Did Document associated with the given `did`, or `null` if no Did Document was found.

    ```kotlin
    suspend fun getDidDocument(did: Did, wallet: Wallet): DidDocument?
    ```

2. Performs a transaction setting the specified `didDocument` as being associated with the
   address present inside the specified `wallet`.

    ```kotlin
    suspend fun setDidDocument(
        didDocument: DidDocument,
        wallet: Wallet,
        fee: StdFee? = null
    ): TxResponse
    ```

3. Creates a new Did power up request for the given `pairwiseDid` and of the given `amount`. Signs everything that needs to be signed (i.e. the signature JSON inside the payload) with the private key contained inside the given `wallet`.

    ```kotlin
    suspend fun requestDidPowerUp(
        pairwiseDid: Did,
        amount: List<StdCoin>,
        wallet: Wallet,
        privateKey: RSAPrivateKey,
        fee: StdFee? = null
    ): TxResponse
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

val didDocument = DidDocumentHelper.fromWallet(
  wallet,
  listOf(
    rsaVerificationKeyPair.publicWrapper,
    rsaSignatureKeyPair.publicWrapper
  )
)

// Set the did document
val response = IdHelper.setDidDocument(
  didDocument,
  wallet
)

// Request a did power up (only after you send a deposit to the Tumbler)
val pairwiseWallet = Wallet.derive(
  mnemonic = mnemonic,
  networkInfo = info,
  lastDerivationPathSegment = 10
)

val depositAmount = listOf(
  StdCoin(denom = "ucommercio", amount = "10")
)

val privateKey = rsaSignatureKeyPair.private as RSAPrivateKey

IdHelper.requestDidPowerUp(
  pairwiseDid = Did(pairwiseWallet.bech32Address),
  amount = depositAmount,
  wallet = wallet,
  privateKey = privateKey
)
```

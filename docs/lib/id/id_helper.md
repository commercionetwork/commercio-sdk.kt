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
suspend fun setDidDocument(didDocument: DidDocument, wallet: Wallet): TxResponse
```
3. Creates a new Did deposit request for the given `recipient` and of the given `amount`.
   Signs everything that needs to be signed (i.e. the signature JSON inside the payload) with the
   private key contained inside the given `wallet`.
```kotlin
suspend fun requestDidDeposit(
    recipient: Did, 
    amount: List<StdCoin>, 
    wallet: Wallet
): TxResponse
```
4. Creates a new Did power up request for the given `pairwiseDid` and of the given `amount`.
   Signs everything that needs to be signed (i.e. the signature JSON inside the payload) with the
   private key contained inside the given `wallet`.
```kotlin
suspend fun requestDidPowerUp(
    pairwiseDid: Did, 
    amount: List<StdCoin>, 
    wallet: Wallet
): TxResponse
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
    
    val userWallet = Wallet.derive(mnemonic = userMnemonic, networkInfo = info)
    
    // Create did document
    val rsaKeyPair = KeysHelper.generateRsaKeyPair()
    val ecKeyPair = KeysHelper.generateEcKeyPair()
    val didDocument = DidDocumentHelper.fromWallet(
        wallet, 
        listOf(rsaKeyPair.public, ecKeyPair.public)
    )
    
    // Set the did document
    val response = IdHelper.setDidDocument(didDocument, userWallet)
    
    
    
    // Request a did deposit
    val depositAmount = listOf(StdCoin(denom = "ucommercio", amount = "10"))
    
    IdHelper.requestDidDeposit(
        recipient = Did(userWallet.bech32Address),
        amount = depositAmount,
        wallet = userWallet
    )
    
    // Request a did power up (only after you made a did deposit request)
    val pairwiseMnemonic = listOf(
            "push",
            "grace",
            "power",
            "desk",
            "arrive",
            "horror",
            "gallery",
            "physical",
            "kingdom",
            "ecology",
            "fat",
            "firm",
            "future",
            "service",
            "table",
            "little",
            "live",
            "reason",
            "maximum",
            "short",
            "motion",
            "planet",
            "stage",
            "second"
    )
     
    val pairwiseWallet = Wallet.derive(
        mnemonic = pairwiseMnemonic, 
        networkInfo = info
    )
        
     
    IdHelper.requestDidPowerUp(
        pairwiseDid = Did(pairwiseWallet.bech32Address),
        amount = depositAmount,
        wallet = userWallet
    )
```

# Id helper
Id helper allows to easily perform all the operations related to the commercio.network `id` module.

## Provided operations
1. Returns the `DidDocument` associated with the given `did`, or `null` if no Did Document was found.
    ```kotlin
    suspend fun getDidDocument(did: Did, wallet: Wallet): DidDocument?
    ```
2. Performs a transaction setting the specified `didDocument` as being associated with the
   address present inside the specified `wallet`.  Optionally `fee` and broadcasting `mode` parameters can be specified.
    ```kotlin
    suspend fun setDidDocument(        
       didDocument: DidDocument,
       wallet: Wallet,
       fee: StdFee? = null,
       mode: BroadcastingMode? = null
    ): TxResponse
    ```
3. Performs a transaction setting the `didDocuments` list as being associated with the address present inside the specified `wallet`. 
   Optionally `fee` and broadcasting `mode` parameters can be specified.
    ```kotlin
    suspend fun setDidDocumentsList(
       didDocuments: List<DidDocument>,
       wallet: Wallet,
       fee: StdFee? = null,
       mode: BroadcastingMode? = null
    ): TxResponse 
    ```
4. Creates a new Did power up request from `wallet` address for the given `pairwiseDid` and of the given `amount`.  
   Signs everything that needs to be signed (i.e. the signature JSON inside the payload) with the
   private key contained inside the given `wallet` and the `private key`.
   Optionally `fee` and broadcasting `mode` parameters can be specified.
    ```kotlin
    suspend fun requestDidPowerUp(
        pairwiseDid: Did,
        amount: List<StdCoin>,
        wallet: Wallet,
        privateKey: RSAPrivateKey,
        fee: StdFee? = null,
        mode: BroadcastingMode? = null
    ): TxResponse
    ```
5. Sends a new transaction from the sender `wallet` to request a list of Did PowerUp `requestDidPowerUpsList`. 
   Optionally `fee` and broadcasting `mode` parameters can be specified.
    ```kotlin
    suspend fun requestDidPowerUpsList(
       requestDidPowerUps: List<RequestDidPowerUp>,
       wallet: Wallet,
       fee: StdFee? = null,
       mode: BroadcastingMode? = null
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
    val rsaVerificationKeyPair = KeysHelper.generateRsaKeyPair()
    val rsaSignatureKeyPair = KeysHelper.generateRsaKeyPair(type = "RsaSignatureKey2018")

    val didDocument = DidDocumentHelper.fromWallet(
        wallet, 
        listOf(rsaVerificationKeyPair.publicKey, rsaSignatureKeyPair.publicKey)
    )

    try {
      
        // Set the did document
        val response = IdHelper.setDidDocument(didDocument, userWallet)
    
        // Send Power Up to the Tumbler
        val msgDeposit = listOf(
            MsgSend(
                amount = listOf(StdCoin(denom = "ucommercio", amount = "100")),
                fromAddress = userWallet.bech32Address,
                toAddress = "did:com:14ttg3eyu88jda8udvxpwjl2pwxemh72w0grsau"
            )
        )

        TxHelper.createSignAndSendTx(
            msgs = msgDeposit,
            wallet = wallet
        )
    
        // Request a did power up
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
        
        val depositAmount = listOf(StdCoin(denom = "ucommercio", amount = "100"))
         
        IdHelper.requestDidPowerUp(
            pairwiseDid = Did(pairwiseWallet.bech32Address),
            amount = depositAmount,
            wallet = userWallet,
            privateKey = rsaSignatureKeyPair.privateKey
        )
    } catch (e: Exception){
        throw e
    }
```

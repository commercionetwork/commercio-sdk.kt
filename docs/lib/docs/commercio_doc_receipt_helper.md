# Commercio Doc Receipt Helper

Allows to easily create a CommercioDocReceipt and perform common related operations.

## Provide Operations

1. Creates a CommercioDoc from the given `wallet`, `recipient`, `txHash`, `documentId` and optionally `proof`.

    ```kotlin
    fun fromWallet(
        wallet: Wallet,
        recipient: Did,
        txHash: String,
        documentId: String,
        proof: String = ""
    ): CommercioDocReceipt
    ```

## Usage examples

Suppose that we received a `MsgShareDocument` with our wallet address in the `recipients` list and we whant to send back
a receipt. Let's assume that we check the transaction
at http://localhost:1337/txs/3959641D57D8B6DE0DE7F71CFB636F3140AB0F8FD9976E996477C6AAD5FBF730 and extract the needed
information to build our receipt in the following example:

```kotlin
// Configure the blockchain network
val info = NetworkInfo(
   bech32Hrp = "did:com:",
   lcdUrl = "http://localhost:1317"
)

// Build our wallet from the mnemonics
val mnemonic = listOf(
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
val wallet = Wallet.derive(mnemonic = mnemonic, networkInfo = info)

// The received MsgShareDocument transaction hash
val txHash = "3959641D57D8B6DE0DE7F71CFB636F3140AB0F8FD9976E996477C6AAD5FBF730"

// The MsgShareDocument sender
val shareDocSender = "did:com:1cc65t29yuwuc32ep2h9uqhnwrregfq230lf2rj"

// The MsgShareDocument UUID
val docId = "63df6ade-d2c7-490b-9191-f56f88c8e5eb"

val commercioDocReceipt = CommercioDocReceiptHelper.fromWallet(
   wallet = wallet,
   recipient = Did(shareDocSender),
   txHash = txHash,
   documentId = docId
)
```


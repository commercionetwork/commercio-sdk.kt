import kotlinx.coroutines.runBlocking
import network.commercio.sacco.*
import network.commercio.sacco.models.messages.MsgSend
import network.commercio.sacco.models.types.StdCoin
import network.commercio.sacco.models.types.StdFee
import network.commercio.sacco.models.types.StdMsg
import network.commercio.sdk.crypto.CertificateHelper
import network.commercio.sdk.crypto.KeysHelper
import network.commercio.sdk.crypto.SignHelper
import network.commercio.sdk.docs.DocsHelper
import network.commercio.sdk.docs.EncryptedData
import network.commercio.sdk.entities.docs.CommercioDoc
import network.commercio.sdk.entities.id.Did
import network.commercio.sdk.entities.membership.MembershipType
import network.commercio.sdk.id.DidDocumentHelper
import network.commercio.sdk.id.IdHelper
import network.commercio.sdk.membership.MembershipHelper
import network.commercio.sdk.mint.MintHelper
import network.commercio.sdk.networking.Network
import network.commercio.sdk.networking.QueryResult
import network.commercio.sdk.tx.TxHelper
import org.junit.Test
import org.kethereum.bip39.generateMnemonic
import org.kethereum.bip39.wordlists.WORDLIST_ENGLISH
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.security.interfaces.RSAPrivateKey
import java.util.*


@Suppress("EXPERIMENTAL_API_USAGE")
class Examples {

// val networkInfo = NetworkInfo(bech32Hrp = "did:com:", lcdUrl = "https://lcd-testnet.commercio.network") //Old

    val lcdUrl = "http://lcd-demo.commercio.network"
    val authority = "faucet-devnet.commercio.network"
    val networkInfo = NetworkInfo(bech32Hrp = "did:com:", lcdUrl = lcdUrl)

    val userMnemonic = listOf(
        "turkey",
        "expire",
        "insect",
        "slim",
        "decline",
        "end",
        "leisure",
        "chimney",
        "cost",
        "wine",
        "lawsuit",
        "rain",
        "mobile",
        "fade",
        "sustain",
        "exclude",
        "pudding",
        "obvious",
        "appear",
        "warrior",
        "hour",
        "humble",
        "trim",
        "adult"
    )

    val userMnemonic1 = listOf(
        "mad",
        "accuse",
        "glove",
        "wife",
        "east",
        "magic",
        "tackle",
        "couple",
        "alien",
        "boy",
        "giant",
        "walk",
        "ring",
        "define",
        "fit",
        "biology",
        "medal",
        "coast",
        "fury",
        "raise",
        "expand",
        "join",
        "wall",
        "eternal"
    )

    val mnemonic_2_3 = listOf(
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

    val storedMnemonic_2_4 =
        "final random flame cinnamon grunt hazard easily mutual resist pond solution define knife female tongue crime atom jaguar alert library best forum lesson rigid"

    val claimerPairWise = listOf(
        "return", "board", "road", "one", "damage", "panel", "else", "demise", "exhibit",
        "swamp", "solution", "success", "behind", "two", "law", "mosquito", "punch", "enough", "friend", "enjoy",
        "pepper", "social", "volume", "envelope"
    )

    val pairwiseMnemonic = listOf(
        "push", "grace", "power", "desk", "arrive", "horror", "gallery", "physical",
        "kingdom", "ecology", "fat", "firm", "future", "service", "table", "little", "live", "reason", "maximum",
        "short", "motion", "planet", "stage", "second"
    )

    val privateKeyPem = """-----BEGIN PRIVATE KEY-----
MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDEhvOUpSsU2DBO
qmIaaeoaRT9uJacVfTKDzqhwscAXLag26xil0rMb3p23olCAtywFhrcB7HR6y8aj
UhCTqVEMB2tUvq13OgPh1QwttIr6XU1IQEOkAHbv3RE9tRfsWHMxcYEvilqFfWeM
crQ9TNADtJuwKE8xu4M2X0VXYp3vNLX9lg3mmbWwvKeAh/Wa6YbMSHSHi1abEAr6
INki/5axbRAtbl/72Wsc8obY821Q+3owCSqVCLxdYHNLlz0SnnYAqQvyk0uXkLlZ
YuMFA4LLPPiSk7pTYvPN8kUhD5mywD1b46grWQcm/wKr9++cbvavXU3DVZpnGGhx
dFs4fuRNAgMBAAECggEACK8t9FOYI8Op3tAIpcIoZiDJPMTNLVXq9YgcSV6RbnTG
Cic5X5p/76tWrNavWADAIT5it+MLAqIelPj7YosI2c86HvSOFcaqhUA02ZAw0/9r
etXcJ1Md7luGd4OZGwiNjpHFc6RZe6ye9sXa0Rqj1oYUmyQs27W9hpmPJt1kvT0z
MyzGH6NXE/OkdGmVBEtrVGmj8H3R7o6TuBc81Fzgv3k8LqmbGdgKguXkuzY0RegA
voaW9KAzGUAtTzuhmxijxfXIR0kri51PIKXsWufT7Oc5bcTPfn9huRYBfNXX8oHV
iKyC3OOMXm0aOhs5jQYoAT1RiLiWNTuK2ntr5tW+wQKBgQDs5wChxGiyX0QIw7js
umLDZK1sfEvXnmmHdyyGSgvunOnIuOrBPb4uxkM39Cv6BrKbqKXBsgleaDO3oNAa
w7hlaNJai+ofbE9gdORAa74AMXAgAB3nmSs1ysP7nOIx5FPiLJDTdND16V8YG1CP
yXZn7yu7rqR8hK0D9jrK188jJwKBgQDUXrf1pOQLKOva19ruMRtOBv1Mc8FnQ1Ld
cQNyWjcYIzA3MvET1HVeuxGjqGi7xD/LkKP8Z1C3pEQyhqDfPQVH4Ip5Fen7qFnb
MdaXNeVvy7tQWgqSyeAIOahm94HYN6LOO98/X7BD+7oGNxEg3OW58TnPx2voVNQM
Qhh4Ik8VawKBgCFRR6x4lATqEwZsiFh6yiMSyLUmMgBsHglYQGHOjikL1nFNWG6/
AT1Q0ulOpNSwFnR/3ngEUv79BrD/o+Tvu8I/YL4tSUsMD4nQRr2sqXcbfNpzScFl
sEcLck6zvLgplYaMn/2tQyoinSYlyUm/mrB5WZw2rpl4i2CwQ4p9mXsnAoGANri9
YB9/31xJAdfzAsNGjOGCLUv6ftg7JvIy+ClE3wRaLS8ELZ7mK2W6ktgXPKLB6Xs4
thYNF3TNWdOU6lD35mB2beL2qrDjjnaWG9p4Z0kw1/CLnu1Du/rXaMjH/WXAnrGQ
weyvJ6VadrWDxVsGGtaMlfyF0pThGtOBKJt2k0cCgYEArfQVouF/daHoTwB1BX+u
LIuxTinZV4Ek3xl5E3LKo4rrY74I2+IbMdSt9E7q8k47BlEnxhixwivjUjsFFIqG
s+vl7a3lB5pdf+Igq8ukfgYMvrK0K3ptl/sTcs4hT0FzXo+6rUKwxHjZxw4tynwz
DqNPFV4qk2gvpDAQ5uvBuX0=
-----END PRIVATE KEY-----
"""
  //  TODO delete this file, used for test

    @Test
    fun `IdHelper examples`() = runBlocking {



//            print("\n-----------------------------------\nchapter3.3 Create_a_Ddo\n-----------------------------------\n")
//            print("\nnetworkInfo: $networkInfo\n")
//
//            //address = "did:com:150jp3tx96frukqg6v870etf02q0cp7em78wu48";
//
//            val wallet = Wallet.derive(mnemonic = userMnemonic1, networkInfo = networkInfo)
//            print("\nwallet: $wallet\n")
//
//
//            print("\nGenerate random pem keys\n")
//            val rsaKeyPair0 = KeysHelper.generateRsaKeyPair()
//            val rsaKeyPair1 = KeysHelper.generateRsaKeyPair(type = "RsaSignatureKey2018")
//
//
//            val didDocument = DidDocumentHelper.fromWallet(
//                wallet,
//                listOf(rsaKeyPair0.publicWrapper, rsaKeyPair1.publicWrapper)
//            )
//            print("\ndidDocument: $didDocument\n")
//
//
//            val response = IdHelper.setDidDocument(
//                didDocument,
//                wallet,
//                fee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "10000")))
//            )
//            print("\nresponse: $response\n")






//            print("\n-----------------------------------\nchapter3.5 Request_Powerup\n-----------------------------------\n")
//            print("\nnetworkInfo: $networkInfo\n")
//
//            val wallet = Wallet.derive(mnemonic = userMnemonic, networkInfo = networkInfo)
//            print("\nwallet: $wallet\n")
//
//            val pairwiseWallet = Wallet.derive(mnemonic = pairwiseMnemonic, networkInfo = networkInfo)
//
//            val depositAmount = listOf(StdCoin(denom = "ucommercio", amount = "10000"))
//            val fee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "10000")))
//            val PrivateCleaned = privateKeyPem.toString()
//                .replace("\n", "")
//                .replace("-----BEGIN PRIVATE KEY-----", "")
//                .replace("-----END PRIVATE KEY-----", "")
//
//            val encoded: ByteArray = Base64.getDecoder().decode(PrivateCleaned)
//            val kf: KeyFactory = KeyFactory.getInstance("RSA")
//            val keySpec = PKCS8EncodedKeySpec(encoded)
//            val privateKeyPem =
//                kf.generatePrivate(keySpec) as RSAPrivateKey
//
//            val response = IdHelper.requestDidPowerUp(
//                pairwiseDid = Did(pairwiseWallet.bech32Address),
//                amount = depositAmount,
//                wallet = wallet,
//                privateKey = privateKeyPem,
//                fee = fee // optional
//            )
//
//            print("\nresponse: $response\n")
//            val resp = response.toString()
//                .replace("Successful(txHash=", "")
//                .replace(")", "")
//            print("\nview: $lcdUrl/txs/$resp\n")



//
//
//            print("\n-----------------------------------\nchapter4.2 ShareDoc\n-----------------------------------\n")
//            print("\nnetworkInfo: $networkInfo\n")
//
//            val senderWallet = Wallet.derive(mnemonic = userMnemonic, networkInfo = networkInfo)
//
//            print("\nsenderWallet: $senderWallet\n")
//
//            val recipientWallet = Wallet.derive(mnemonic = userMnemonic1, networkInfo = networkInfo)
//
//            print("\nrecipientWallet: $recipientWallet\n")
//
//            val docRecipientDid = Did(recipientWallet.bech32Address)
//            print("\ndocRecipientDid: $docRecipientDid\n")
//
//            val docId = UUID.randomUUID().toString()
//
//
//            var checksum = CommercioDoc.Checksum(
//                value = "bd29066606c7496d3e0bae11b1c7a3557ed0881535673e81c9a3ed4f",
//                algorithm = CommercioDoc.Checksum.Algorithm.SHA224
//            )
//
//            val doSign = CommercioDoc.CommercioDoSign(
//                storageUri = "http://www.commercio.network",
//                signerIstance = "did:com:1cc65t29yuwuc32ep2h9uqhnwrregfq230lf2rj",
//                sdnData = listOf(
//                    CommercioDoc.CommercioDoSign.CommercioSdnData.COMMON_NAME,
//                    CommercioDoc.CommercioDoSign.CommercioSdnData.SURNAME
//                ),
//                vcrId = "xxxxx",
//                certificateProfile = "xxxxx"
//            )
//
//
//            val response = DocsHelper.shareDocument(
//                id = docId,
//                contentUri = "https://example.com/document",
//                metadata = CommercioDoc.Metadata(
//                    contentUri = "https://example.com/document/metadata",
//                    schema = CommercioDoc.Metadata.Schema(
//                        uri = "https://example.com/custom/metadata/schema",
//                        version = "1.0.0"
//                    )
//                ),
//                recipients = listOf(docRecipientDid),
//                fee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "10000"))),
//                wallet = senderWallet,
//                checksum = checksum,
//                doSign = doSign
//                , encryptedData = listOf(EncryptedData.METADATA_CONTENT_URI)
//            )
//
//            print("\nresponse: $response\n")



//        suspend fun chapter4_3_SendReceipt() {
//            print("\n-----------------------------------\nchapter4.3 SendReceipt\n-----------------------------------\n")
//            print("\nnetworkInfo: $networkInfo\n")
//
//            val mnemonic = listOf(
//                "curve",
//                "attend",
//                "elephant",
//                "garage",
//                "tide",
//                "neither",
//                "enforce",
//                "auction",
//                "dumb",
//                "brief",
//                "divert",
//                "creek",
//                "palm",
//                "equip",
//                "festival",
//                "spice",
//                "race",
//                "message",
//                "domain",
//                "seed",
//                "ship",
//                "hunt",
//                "mercy",
//                "mail"
//            )
//            val wallet = Wallet.derive(mnemonic = mnemonic, networkInfo = networkInfo)
//            print("\nwallet: $wallet\n")
//
//            val recipientMnemonic = listOf(
//                "push",
//                "grace",
//                "power",
//                "desk",
//                "arrive",
//                "horror",
//                "gallery",
//                "physical",
//                "kingdom",
//                "ecology",
//                "fat",
//                "firm",
//                "future",
//                "service",
//                "table",
//                "little",
//                "live",
//                "reason",
//                "maximum",
//                "short",
//                "motion",
//                "planet",
//                "stage",
//                "second"
//            )
//            val recipientWallet = Wallet.derive(recipientMnemonic, networkInfo)
//            print("\nrecipientWallet: $recipientWallet\n")
//
//            val docRecipientDid = Did(recipientWallet.bech32Address)
//            print("\ndocRecipientDid: $docRecipientDid\n")
//
//            val docId = UUID.randomUUID().toString()
//
//            val response = DocsHelper.shareDocument(
//                id = docId,
//                contentUri = "https://example.com/document",
//                metadata = CommercioDoc.Metadata(
//                    contentUri = "https://example.com/document/metadata",
//                    schema = CommercioDoc.Metadata.Schema(
//                        uri = "https://example.com/custom/metadata/schema",
//                        version = "1.0.0"
//                    )
//                ),
//                recipients = listOf(docRecipientDid),
//                fee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "10000"))),
//                wallet = wallet
//            )
//            print("\nshareDocument response: $response\n")
//
//            if (response is TxResponse.Successful) {
//                val txHash = response.txHash
//                print("\ntxHash in shareDocument response: $txHash\n")
//
//
//                val receiptRecipientDid = Did(wallet.bech32Address)
//                print("\nreceiptRecipientDid: $receiptRecipientDid\n")
//
//                val fee = StdFee(gas = "200000", amount = listOf(StdCoin(denom = "ucommercio", amount = "10000")))
//                val responseSendDocumentReceipt = DocsHelper.sendDocumentReceipt(
//                    recipient = receiptRecipientDid,
//                    txHash = txHash,
//                    documentId = docId,
//                    wallet = recipientWallet,
//                    fee = fee // optional
//                )
//
//                print("\nsendDocumentReceipt response: $responseSendDocumentReceipt\n")
//            }
//
//        }
//
//        suspend fun chapter4_4_DocumentList() {
//            print("\n-----------------------------------\nchapter4.4 DocumentList\n-----------------------------------\n")
//            print("\nnetworkInfo: $networkInfo\n")
//
//            val mnemonic = listOf(
//                "curve",
//                "attend",
//                "elephant",
//                "garage",
//                "tide",
//                "neither",
//                "enforce",
//                "auction",
//                "dumb",
//                "brief",
//                "divert",
//                "creek",
//                "palm",
//                "equip",
//                "festival",
//                "spice",
//                "race",
//                "message",
//                "domain",
//                "seed",
//                "ship",
//                "hunt",
//                "mercy",
//                "mail"
//            )
//            val wallet = Wallet.derive(mnemonic = mnemonic, networkInfo = networkInfo)
//            print("\nwallet: $wallet\n")
//
//            val sentDocsURL = "${wallet.networkInfo.lcdUrl}/docs/${wallet.bech32Address}/sent"
//            print("\nsentDocsURL: $sentDocsURL\n")
//
//            val sentDocs = Network.queryChain<List<Objects>>(url = sentDocsURL)
//            print("\nsentDocs: $sentDocs\n")
//
//            val receivedDocsURL = "${wallet.networkInfo.lcdUrl}/docs/${wallet.bech32Address}/received"
//            print("\nreceivedDocsURL: $receivedDocsURL\n")
//
//            val receivedDocs = Network.queryChain<List<Objects>>(url = receivedDocsURL)
//            print("\nreceivedDocs: $receivedDocs\n")
//
//        }
//
//        suspend fun chapter4_5_ReceiptList() {
//            print("\n-----------------------------------\nchapter4.5 ReceiptList\n-----------------------------------\n")
//            print("\nnetworkInfo: $networkInfo\n")
//
//            val mnemonic = listOf(
//                "curve",
//                "attend",
//                "elephant",
//                "garage",
//                "tide",
//                "neither",
//                "enforce",
//                "auction",
//                "dumb",
//                "brief",
//                "divert",
//                "creek",
//                "palm",
//                "equip",
//                "festival",
//                "spice",
//                "race",
//                "message",
//                "domain",
//                "seed",
//                "ship",
//                "hunt",
//                "mercy",
//                "mail"
//            )
//            val wallet = Wallet.derive(mnemonic = mnemonic, networkInfo = networkInfo)
//            print("\nwallet: $wallet\n")
//
//            val sentReceiptsURL = "${wallet.networkInfo.lcdUrl}/receipts/${wallet.bech32Address}/sent"
//            print("\nsentReceiptsURL: $sentReceiptsURL\n")
//
//            val sentReceipts = Network.queryChain<List<Objects>>(url = sentReceiptsURL)
//            print("\nsentReceipts: $sentReceipts\n") //empty list
//
//            val receivedReceiptsURL = "${wallet.networkInfo.lcdUrl}/receipts/${wallet.bech32Address}/received"
//            print("\nreceivedReceiptsURL: $receivedReceiptsURL\n")
//
//            val receivedReceipts = Network.queryChain<List<Objects>>(url = receivedReceiptsURL)
//            print("\nreceivedReceipts: $receivedReceipts\n")
//        }


    }
}

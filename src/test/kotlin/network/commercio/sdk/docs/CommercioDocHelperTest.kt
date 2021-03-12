package network.commercio.sdk

import kotlinx.coroutines.runBlocking
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.Wallet
import network.commercio.sdk.crypto.KeysHelper
import network.commercio.sdk.docs.CommercioDocHelper
import network.commercio.sdk.entities.docs.CommercioDoc
import network.commercio.sdk.entities.docs.EncryptedData
import network.commercio.sdk.entities.id.Did
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.util.*


@Suppress("EXPERIMENTAL_API_USAGE")
class CommercioDocHelperTest {

    private val info = NetworkInfo(bech32Hrp = "did=com=", lcdUrl = "")

    val mnemonicString =
        "dash ordinary anxiety zone slot rail flavor tortoise guilt divert pet sound ostrich increase resist short ship lift town ice split payment round apology"
    val mnemonic = mnemonicString.split(" ")
    val wallet = Wallet.derive(mnemonic, info)

    val recipientDids = listOf(Did(wallet.bech32Address), Did(wallet.bech32Address))
    val uuid = UUID.randomUUID().toString()

    val metadata = CommercioDoc.Metadata(
        contentUri = "https://example.com/document/metadata",
        schema = CommercioDoc.Metadata.Schema(
            uri = "https://example.com/custom/metadata/schema",
            version = "1.0.0"
        )
    )

    val checksum = CommercioDoc.Checksum(
        value = "6b0c64c6c34bc444407f9d4d940b037e317b5e9e962ecdf70b81a076a9171905",
        algorithm = CommercioDoc.Checksum.Algorithm.SHA256
    )

    @Test
    fun `fromWallet builds CommercioDoc correctly`() = runBlocking {

        val expectedCommercioDoc = CommercioDoc(
            senderDid = wallet.bech32Address,
            uuid = uuid,
            metadata = metadata,
            recipientsDids = listOf(wallet.bech32Address, wallet.bech32Address),
            checksum = checksum,
            contentUri = "https://example.com/document"
        )

        val commercioDoc = CommercioDocHelper.fromWallet(
            id = uuid,
            metadata = metadata,
            recipients = recipientDids,
            wallet = wallet,
            checksum = checksum,
            aesKey = KeysHelper.generateAesKey(),
            contentUri = "https://example.com/document"
        )

        assertEquals(commercioDoc.toString(), expectedCommercioDoc.toString())

        val commercioDocWithEncryptedData = CommercioDocHelper.fromWallet(
            id = uuid,
            metadata = metadata,
            recipients = recipientDids,
            wallet = wallet,
            contentUri = "contentUri",
            encryptedData = listOf(EncryptedData.CONTENT_URI)
        )

        assertNotEquals(commercioDocWithEncryptedData.contentUri, expectedCommercioDoc.contentUri)
    }


    @Test(expected = IllegalArgumentException::class)
    fun `fromWallet should throw an IllegalArgumentException if passing CONTENT_URI with null contentUri param`() =
        runBlocking {
            val commercioDoc = CommercioDocHelper.fromWallet(
                id = uuid,
                metadata = metadata,
                recipients = recipientDids,
                wallet = wallet,
                encryptedData = listOf(EncryptedData.CONTENT_URI)
            )
        }


    @Test(expected = IllegalArgumentException::class)
    fun `fromWallet should throw an IllegalArgumentException if passing METADATA_SCHEMA_URI with null metadata schema param`() =
        runBlocking {

            val commercioDoc = CommercioDocHelper.fromWallet(
                wallet = wallet,
                recipients = recipientDids,
                id = uuid,
                metadata = CommercioDoc.Metadata(
                    contentUri = "https://example.com/document/metadata",
                    schema = null,
                    schemaType = "schemaType"
                ),
                encryptedData = listOf(EncryptedData.METADATA_SCHEMA_URI)
            )
        }

}
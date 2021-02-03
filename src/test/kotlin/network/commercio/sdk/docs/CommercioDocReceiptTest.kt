package network.commercio.sdk.docs

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import network.commercio.sdk.entities.docs.CommercioDocReceipt
import org.junit.Assert.assertEquals
import org.junit.Test

class CommercioDocReceiptTest {


    val correctDid = "did=com=1acdefg"
    val correctUuid = "c510755c-c27d-4348-bf4c-f6050fc6935c"
    val correctCommercioDocReceipt = CommercioDocReceipt(
        uuid = correctUuid,
        senderDid = correctDid,
        recipientDid = correctDid,
        txHash = "txHash",
        documentUuid = correctUuid
    )

    // PROPS

    @Test
    fun `convertValue from map to CommercioDocReceipt should behave correctly`() {

        val jsonMinimal = mutableMapOf<String, Any>()
        jsonMinimal["uuid"] = correctCommercioDocReceipt.uuid
        jsonMinimal["sender"] = correctCommercioDocReceipt.senderDid
        jsonMinimal["recipient"] = correctCommercioDocReceipt.recipientDid
        jsonMinimal["tx_hash"] = correctCommercioDocReceipt.txHash
        jsonMinimal["document_uuid"] = correctCommercioDocReceipt.documentUuid

        val docWithProof = CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = correctDid,
            recipientDid = correctDid,
            txHash = "txHash",
            documentUuid = correctUuid,
            proof = "proof"
        )

        val jsonWithProof = mutableMapOf<String, Any>()
        jsonWithProof.putAll(jsonMinimal)
        jsonWithProof["proof"] = docWithProof.proof

        assertEquals(
            correctCommercioDocReceipt,
            jacksonObjectMapper().convertValue(jsonMinimal, CommercioDocReceipt::class.java)
        )
        assertEquals(docWithProof, jacksonObjectMapper().convertValue(jsonWithProof, CommercioDocReceipt::class.java))
    }


    // UUID

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because UUID requires a not empty value`() {
        CommercioDocReceipt(
            uuid = "",
            senderDid = correctDid,
            recipientDid = correctDid,
            txHash = "",
            documentUuid = correctUuid
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because UUID requires a valid UUID v4 format`() {
        CommercioDocReceipt(
            uuid = "a1b2c3d4",
            senderDid = correctDid,
            recipientDid = correctDid,
            txHash = "",
            documentUuid = correctUuid
        )
    }

    // DOCUMENT_UUID

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because DOCUMENT_UUID requires a not empty value`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = correctDid,
            recipientDid = correctDid,
            txHash = "",
            documentUuid = ""
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because DOCUMENT_UUID requires a valid UUID v4 format`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = correctDid,
            recipientDid = correctDid,
            txHash = "",
            documentUuid = "a1b2c3d4"
        )
    }

    // SENDER

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because SENDER requires not empty value`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = "",
            recipientDid = correctDid,
            txHash = "txHash",
            documentUuid = correctUuid
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because SENDER requires a valid Bech32 format`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = "did:com",
            recipientDid = correctDid,
            txHash = "txHash",
            documentUuid = correctUuid
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because SENDER requires a valid Bech32 format (missing 1 as separator)`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = "did:com:abcdefg",
            recipientDid = correctDid,
            txHash = "txHash",
            documentUuid = correctUuid
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because SENDER requires a valid Bech32 format (too short string)`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = "a1c",
            recipientDid = correctDid,
            txHash = "txHash",
            documentUuid = correctUuid
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because SENDER requires a valid Bech32 format (too long string)`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = "Y3UexQW1ZC6uXcM0ux58mnR3x4zvYaHAEA05DaC03CTcw0mmE0CaK89YD6CHmEUa05k57Dh0506CMUdNzn7QVvgYS80a5Q75lzQK",
            recipientDid = correctDid,
            txHash = "txHash",
            documentUuid = correctUuid
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because SENDER requires a valid Bech32 format (character b must not be contained)`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = "did:com:1abcdef",
            recipientDid = correctDid,
            txHash = "txHash",
            documentUuid = correctUuid
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because SENDER requires a valid Bech32 format (character i must not be contained)`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = "did:com:1aicdefg",
            recipientDid = correctDid,
            txHash = "txHash",
            documentUuid = correctUuid
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because SENDER requires a valid Bech32 format (character o must not be contained)`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = "did:com:1aocdefg",
            recipientDid = correctDid,
            txHash = "txHash",
            documentUuid = correctUuid
        )
    }


    // RECIPIENT

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because RECIPIENT requires not empty value`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = correctDid,
            recipientDid = "",
            txHash = "txHash",
            documentUuid = correctUuid
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because RECIPIENT requires a valid Bech32 format`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = correctDid,
            recipientDid = "did:com",
            txHash = "txHash",
            documentUuid = correctUuid
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because RECIPIENT requires a valid Bech32 format (missing 1 as separator)`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = correctDid,
            recipientDid = "did:com:abcdefg",
            txHash = "txHash",
            documentUuid = correctUuid
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because RECIPIENT requires a valid Bech32 format (too short string)`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = correctDid,
            recipientDid = "a1c",
            txHash = "txHash",
            documentUuid = correctUuid
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because RECIPIENT requires a valid Bech32 format (too long string)`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = correctDid,
            recipientDid = "Y3UexQW1ZC6uXcM0ux58mnR3x4zvYaHAEA05DaC03CTcw0mmE0CaK89YD6CHmEUa05k57Dh0506CMUdNzn7QVvgYS80a5Q75lzQK",
            txHash = "txHash",
            documentUuid = correctUuid
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because RECIPIENT requires a valid Bech32 format (character b must not be contained)`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = correctDid,
            recipientDid = "did:com:1abcdef",
            txHash = "txHash",
            documentUuid = correctUuid
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because RECIPIENT requires a valid Bech32 format (character i must not be contained)`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = correctDid,
            recipientDid = "did:com:1aicdefg",
            txHash = "txHash",
            documentUuid = correctUuid
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDocReceipt should throw an IllegalArgumentException because RECIPIENT requires a valid Bech32 format (character o must not be contained)`() {
        CommercioDocReceipt(
            uuid = correctUuid,
            senderDid = correctDid,
            recipientDid = "did:com:1aocdefg",
            txHash = "txHash",
            documentUuid = correctUuid
        )
    }


}
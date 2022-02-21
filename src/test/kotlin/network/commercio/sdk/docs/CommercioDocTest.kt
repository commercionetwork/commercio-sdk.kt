package network.commercio.sdk.docs


import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import network.commercio.sdk.entities.docs.CommercioDoc
import network.commercio.sdk.entities.docs.EncryptedData
import org.junit.Assert.assertEquals
import org.junit.Test


class CommercioDocTest {

    val correctCommercioDocMetadataSchema = CommercioDoc.Metadata.Schema(
        uri = "http=//uri.url",
        version = "1"
    )
    val correctMetadata = CommercioDoc.Metadata(
        contentUri = "content-uri",
        schemaType = "schemaType",
        schema = correctCommercioDocMetadataSchema
    )
    val correctDid = "did=com=1acdefg"
    val correctUuid = "c510755c-c27d-4348-bf4c-f6050fc6935c"
    val invalid520CharactersString =
        "qYxEIgza4dfuLGka6ULnNLv8PArpjjuLoipUyoi2xon3sHAcufys3o89jRYUuGlDs68qd1NsJKAxC11InzFJBXYMaOYHFniELo2OfRN52SQReR1sShgwX5oQboUc38yITigc6pw4oBOMlz895pChLfXDAHDQSon9D11hc7AX4QkGqWxH5gdvZgrkRxTDckMJCC0mhxWi9brwwgLeTqH3sjwmVPDB5KDGMw1inp8oRSn563TEKPqd1Pp1pb06N81pI2ACkPKnLpDvYVE75vCITq8FhBBlV3neuSg5ktfAjaZ3byev0MsnPv2gwakpSgNWbVAumEA0OJuzsYDytBhUIyAM9zTpKVoVOYzks4W7jRHdiiqXs7OiyXCrgQwVyKDW1eAM9NYexYf9dUfnYja4RxVP0GSIhefun39LgrgpNDjvq2Cbrx296WGt6GLUKDxhqScPmVkvmSzT7ULklJztrA3oE3ooNVSWbq5ir772lKiuhhtFZEaPWSGeeHdmWodQoOWFFWfC";
    val invalid33CharsString = "bKWAUWc2oBRIxFjIJrGFrT9RohFC4hXLe"
    val invalid65CharsString =
        "9S9BRIhrhaGcUvoothkdkvcil1a1Kn9AROHh4hLRCxSmZbDbfYy2NP5NjpaAQH1iX"
    val correctCommercioDocEncryptionDataKey = CommercioDoc.EncryptionData.Key(
        recipientDid = correctDid,
        value = "value"
    )
    val correctCommercioDocChecksum = CommercioDoc.Checksum(
        value = "value",
        algorithm = CommercioDoc.Checksum.Algorithm.MD5
    )

    val correctCommercioDoSign = CommercioDoc.CommercioDoSign(
        storageUri = "http=//do.sign",
        signerIstance = "signer",
        vcrId = "vcrId",
        certificateProfile = "profile",
        sdnData = listOf(
            CommercioDoc.CommercioDoSign.CommercioSdnData.COMMON_NAME
        )
    )

    val correctCommercioEncryptionData = CommercioDoc.EncryptionData(
        keys = listOf(correctCommercioDocEncryptionDataKey),
        encryptedData = listOf(EncryptedData.CONTENT_URI)
    )

    val correctCommercioDoc = CommercioDoc(
        senderDid = correctDid,
        recipientsDids = listOf(correctDid),
        uuid = correctUuid,
        metadata = correctMetadata,
        contentUri = "content-uri",
        checksum = correctCommercioDocChecksum,
        doSign = correctCommercioDoSign,
        encryptionData = correctCommercioEncryptionData
    )

    // SENDER

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException because SENDER requires not empty value`() {
        CommercioDoc(
            senderDid = "",
            recipientsDids = listOf(),
            uuid = correctUuid,
            metadata = correctMetadata
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException because SENDER requires a valid Bech32 format`() {
        CommercioDoc(
            senderDid = "did:com",
            recipientsDids = listOf(correctDid),
            uuid = correctUuid,
            metadata = correctMetadata
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException because SENDER requires a valid Bech32 format (missing 1 as separator)`() {
        CommercioDoc(
            senderDid = "did:com:abcdefg",
            recipientsDids = listOf(correctDid),
            uuid = correctUuid,
            metadata = correctMetadata
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException because SENDER requires a valid Bech32 format (too short string)`() {
        CommercioDoc(
            senderDid = "a1c",
            recipientsDids = listOf(correctDid),
            uuid = correctUuid,
            metadata = correctMetadata
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException because SENDER requires a valid Bech32 format (too long string)`() {
        CommercioDoc(
            senderDid = "Y3UexQW1ZC6uXcM0ux58mnR3x4zvYaHAEA05DaC03CTcw0mmE0CaK89YD6CHmEUa05k57Dh0506CMUdNzn7QVvgYS80a5Q75lzQK",
            recipientsDids = listOf(correctDid),
            uuid = correctUuid,
            metadata = correctMetadata
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException because SENDER requires a valid Bech32 format (character b must not be contained)`() {
        CommercioDoc(
            senderDid = "did:com:1abcdef",
            recipientsDids = listOf(correctDid),
            uuid = correctUuid,
            metadata = correctMetadata
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException because SENDER requires a valid Bech32 format (character i must not be contained)`() {
        CommercioDoc(
            senderDid = "did:com:1aicdefg",
            recipientsDids = listOf(correctDid),
            uuid = correctUuid,
            metadata = correctMetadata
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException because SENDER requires a valid Bech32 format (character o must not be contained)`() {
        CommercioDoc(
            senderDid = "did:com:1aocdefg",
            recipientsDids = listOf(correctDid),
            uuid = correctUuid,
            metadata = correctMetadata
        )
    }

    // RECIPIENTS

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException if passing RECIPIENTS as empty list`() {
        CommercioDoc(
            senderDid = correctDid,
            recipientsDids = listOf(),
            uuid = correctUuid,
            metadata = correctMetadata
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException because RECIPIENTS requires a list of not empty string`() {
        CommercioDoc(
            senderDid = correctDid,
            recipientsDids = listOf(""),
            uuid = correctUuid,
            metadata = correctMetadata
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException because RECIPIENTS requires a list of string in valid Bech32 format`() {
        CommercioDoc(
            senderDid = correctDid,
            recipientsDids = listOf("did:com"),
            uuid = correctUuid,
            metadata = correctMetadata
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException because RECIPIENTS requires a list of string in valid Bech32 format (missing 1 as separator)`() {
        CommercioDoc(
            senderDid = correctDid,
            recipientsDids = listOf("did:com:abcdefg"),
            uuid = correctUuid,
            metadata = correctMetadata
        )
    }


    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException because RECIPIENTS requires a list of string in valid Bech32 format (too short string)`() {
        CommercioDoc(
            senderDid = correctDid,
            recipientsDids = listOf("a1c"),
            uuid = correctUuid,
            metadata = correctMetadata
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException because RECIPIENTS requires a list of string in valid Bech32 format (character b must not be contained)`() {
        CommercioDoc(
            senderDid = correctDid,
            recipientsDids = listOf("did:com:1abcdef"),
            uuid = correctUuid,
            metadata = correctMetadata
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException because RECIPIENTS requires a list of string in valid Bech32 format (character i must not be contained)`() {
        CommercioDoc(
            senderDid = correctDid,
            recipientsDids = listOf("did:com:1aicdefg"),
            uuid = correctUuid,
            metadata = correctMetadata
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException because RECIPIENTS requires a list of string in valid Bech32 format (character o must not be contained)`() {
        CommercioDoc(
            senderDid = correctDid,
            recipientsDids = listOf("did:com:1aocdefg"),
            uuid = correctUuid,
            metadata = correctMetadata
        )
    }

    // UUID

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException because UUID requires a valid UUID v4 format)`() {
        CommercioDoc(
            senderDid = correctDid,
            recipientsDids = listOf(correctDid),
            uuid = "a1b2c3d4",
            metadata = correctMetadata
        )
    }

    // CONTENT URI

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc should throw an IllegalArgumentException because, if contentUri is provided, it should be less than 512 bytes len)`() {
        CommercioDoc(
            senderDid = correctDid,
            recipientsDids = listOf(correctDid),
            uuid = correctUuid,
            metadata = correctMetadata,
            contentUri = invalid520CharactersString
        )
    }

    // PROPS

    @Test
    fun `fromJson should behave correctly`() {

        val fullCommercioDocObjSerialized = jacksonObjectMapper().writeValueAsString(correctCommercioDoc)
        val fullJson =
            """{"sender":"did=com=1acdefg","recipients":["did=com=1acdefg"],"UUID":"c510755c-c27d-4348-bf4c-f6050fc6935c","contentUri":"content-uri","metadata":{"content_uri":"content-uri","schema":{"uri":"http=//uri.url","version":"1"},"schema_type":"schemaType"},"checksum":{"value":"value","algorithm":"md5"},"encryption_data":{"keys":[{"recipient":"did=com=1acdefg","value":"value"}],"encrypted_data":["content_uri"]},"do_sign":{"storage_uri":"http=//do.sign","signer_instance":"signer","vcr_id":"vcrId","certificate_profile":"profile","sdn_data":["common_name"]}}"""
        assertEquals(fullCommercioDocObjSerialized, fullJson)

        val minimalDocChecksumNull = CommercioDoc(
            uuid = correctCommercioDoc.uuid,
            senderDid = correctCommercioDoc.senderDid,
            recipientsDids = correctCommercioDoc.recipientsDids,
            metadata = correctCommercioDoc.metadata
        )

        val minimalDocChecksumNullSerialized = jacksonObjectMapper().writeValueAsString(minimalDocChecksumNull)
        val minimalDocChecksumNullJson =
            """{"sender":"did=com=1acdefg","recipients":["did=com=1acdefg"],"UUID":"c510755c-c27d-4348-bf4c-f6050fc6935c","metadata":{"content_uri":"content-uri","schema":{"uri":"http=//uri.url","version":"1"},"schema_type":"schemaType"}}""".trimMargin()
        assertEquals(minimalDocChecksumNullSerialized, minimalDocChecksumNullJson)

        val minimalDocSdnDataNull = CommercioDoc(
            uuid = correctCommercioDoc.uuid,
            senderDid = correctCommercioDoc.senderDid,
            recipientsDids = correctCommercioDoc.recipientsDids,
            metadata = correctCommercioDoc.metadata,
            doSign = CommercioDoc.CommercioDoSign(
                storageUri = correctCommercioDoc.doSign!!.storageUri,
                signerIstance = correctCommercioDoc.doSign!!.signerIstance,
                vcrId = correctCommercioDoc.doSign!!.vcrId,
                certificateProfile = correctCommercioDoc.doSign!!.certificateProfile
            ),
            checksum = correctCommercioDoc.checksum
        )

        val minimalDocSdnDataNullSerialized = jacksonObjectMapper().writeValueAsString(minimalDocSdnDataNull)
        val minimalDocSdnDataNullJson =
            """{"sender":"did=com=1acdefg","recipients":["did=com=1acdefg"],"UUID":"c510755c-c27d-4348-bf4c-f6050fc6935c","metadata":{"content_uri":"content-uri","schema":{"uri":"http=//uri.url","version":"1"},"schema_type":"schemaType"},"checksum":{"value":"value","algorithm":"md5"},"do_sign":{"storage_uri":"http=//do.sign","signer_instance":"signer","vcr_id":"vcrId","certificate_profile":"profile","sdn_data":null}}""".trimMargin()
        assertEquals(minimalDocSdnDataNullSerialized, minimalDocSdnDataNullJson)
    }

    @Test
    fun `convertValue from map to CommercioDoc should behave correctly`() {

        val jsonMinimal = mutableMapOf<String, Any>()
        jsonMinimal["sender"] = correctCommercioDoc.senderDid
        jsonMinimal["recipients"] = correctCommercioDoc.recipientsDids
        jsonMinimal["UUID"] = correctCommercioDoc.uuid
        jsonMinimal["metadata"] = correctCommercioDoc.metadata

        val jsonWithContentUri = mutableMapOf<String, Any>()
        jsonWithContentUri.putAll(jsonMinimal)
        jsonWithContentUri["contentUri"] = correctCommercioDoc.contentUri

        val checksumMap = mutableMapOf<String, Any>()
        checksumMap["algorithm"] = CommercioDoc.Checksum.Algorithm.MD5
        checksumMap["value"] = correctCommercioDocChecksum.value

        val jsonWithChecksum = mutableMapOf<String, Any>()
        jsonWithChecksum.putAll(jsonMinimal)
        jsonWithChecksum["checksum"] = checksumMap

        val encryptionDataKeyMap = mutableMapOf<String, Any>()
        encryptionDataKeyMap["recipient"] = correctCommercioDocEncryptionDataKey.recipientDid
        encryptionDataKeyMap["value"] = correctCommercioDocEncryptionDataKey.value

        val encryptionDataMap = mutableMapOf<String, Any>()
        encryptionDataMap["keys"] = listOf(encryptionDataKeyMap)
        encryptionDataMap["encrypted_data"] = listOf(EncryptedData.CONTENT_URI)

        val jsonWithEncryptionData = mutableMapOf<String, Any>()
        jsonWithEncryptionData.putAll(jsonMinimal)
        jsonWithEncryptionData["encryption_data"] = encryptionDataMap

        val doSignMap = mutableMapOf<String, Any>()
        doSignMap["storage_uri"] = correctCommercioDoSign.storageUri
        doSignMap["signer_instance"] = correctCommercioDoSign.signerIstance
        doSignMap["vcr_id"] = correctCommercioDoSign.vcrId
        doSignMap["certificate_profile"] = correctCommercioDoSign.certificateProfile
        doSignMap["sdn_data"] = listOf(CommercioDoc.CommercioDoSign.CommercioSdnData.COMMON_NAME)

        val jsonWithDoSign = mutableMapOf<String, Any>()
        jsonWithDoSign.putAll(jsonMinimal)
        jsonWithDoSign["do_sign"] = doSignMap
        jsonWithDoSign["checksum"] = checksumMap

        val minimalDoc = CommercioDoc(
            uuid = correctCommercioDoc.uuid,
            senderDid = correctCommercioDoc.senderDid,
            recipientsDids = correctCommercioDoc.recipientsDids,
            metadata = correctCommercioDoc.metadata
        )

        assertEquals(minimalDoc, jacksonObjectMapper().convertValue(jsonMinimal, CommercioDoc::class.java))
        assertEquals(minimalDoc.checksum, null)
        assertEquals(minimalDoc.doSign, null)

        val docWithContentUri = CommercioDoc(
            uuid = correctCommercioDoc.uuid,
            senderDid = correctCommercioDoc.senderDid,
            recipientsDids = correctCommercioDoc.recipientsDids,
            metadata = correctCommercioDoc.metadata,
            contentUri = correctCommercioDoc.contentUri
        )

        assertEquals(docWithContentUri, jacksonObjectMapper().convertValue(docWithContentUri, CommercioDoc::class.java))

        val docWithChecksum = CommercioDoc(
            uuid = correctCommercioDoc.uuid,
            senderDid = correctCommercioDoc.senderDid,
            recipientsDids = correctCommercioDoc.recipientsDids,
            metadata = correctCommercioDoc.metadata,
            checksum = correctCommercioDoc.checksum
        )
        assertEquals(docWithChecksum, jacksonObjectMapper().convertValue(jsonWithChecksum, CommercioDoc::class.java))

        val docWithEncryptionData = CommercioDoc(
            uuid = correctCommercioDoc.uuid,
            senderDid = correctCommercioDoc.senderDid,
            recipientsDids = correctCommercioDoc.recipientsDids,
            metadata = correctCommercioDoc.metadata,
            encryptionData = correctCommercioDoc.encryptionData
        )
        assertEquals(
            docWithEncryptionData,
            jacksonObjectMapper().convertValue(jsonWithEncryptionData, CommercioDoc::class.java)
        )

        val docWithDoSign = CommercioDoc(
            uuid = correctCommercioDoc.uuid,
            senderDid = correctCommercioDoc.senderDid,
            recipientsDids = correctCommercioDoc.recipientsDids,
            metadata = correctCommercioDoc.metadata,
            doSign = correctCommercioDoc.doSign,
            checksum = correctCommercioDoc.checksum
        )
        assertEquals(docWithDoSign, jacksonObjectMapper().convertValue(jsonWithDoSign, CommercioDoc::class.java))

    }

    // METADATA

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc_Metadata  should throw an IllegalArgumentException because Metadata_contentUri should be less then 512 bytes)`() {
        CommercioDoc.Metadata(contentUri = invalid520CharactersString)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc_Metadata  should throw an IllegalArgumentException because at least one of schemaType or schema should not be null )`() {
        CommercioDoc.Metadata(contentUri = "content-uri", schema = null)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc_Metadata  should throw an IllegalArgumentException because schemaType should not be empty )`() {
        CommercioDoc.Metadata(contentUri = "content-uri", schemaType = "")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc_Metadata  should throw an IllegalArgumentException because schemaType must have a valid length )`() {
        CommercioDoc.Metadata(contentUri = "content-uri", schemaType = invalid520CharactersString)
    }

    @Test
    fun `convertValue from map to CommercioDoc_Metadata should behave correctly`() {

        val jsonWithSchemaType = mutableMapOf<String, Any>()
        jsonWithSchemaType["content_uri"] = correctMetadata.contentUri
        jsonWithSchemaType["schema_type"] = correctMetadata.schemaType


        val jsonWithSchema = mutableMapOf<String, Any>()
        jsonWithSchema["content_uri"] = correctMetadata.contentUri
        jsonWithSchema["schema"] =
            mutableMapOf("uri" to correctMetadata.schema?.uri, "version" to correctMetadata.schema?.version)

        val metadataWithSchemaType = CommercioDoc.Metadata(
            contentUri = correctMetadata.contentUri,
            schemaType = correctMetadata.schemaType
        )

        val metadataWithSchema = CommercioDoc.Metadata(
            contentUri = correctMetadata.contentUri,
            schema = correctMetadata.schema
        )

        assertEquals(
            metadataWithSchemaType,
            jacksonObjectMapper().convertValue(jsonWithSchemaType, CommercioDoc.Metadata::class.java)
        )
        assertEquals(
            metadataWithSchema,
            jacksonObjectMapper().convertValue(jsonWithSchema, CommercioDoc.Metadata::class.java)
        )

    }

    // METADATA.SCHEMA

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc_Metadata_Schema  should throw an IllegalArgumentException because metadata_schema_uri must have a valid length (less than 512 bytes) )`() {
        CommercioDoc.Metadata.Schema(
            uri = invalid520CharactersString,
            version = "1"
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc_Metadata_Schema  should throw an IllegalArgumentException because metadata_schema_version must have a valid length (less than 512 bytes) )`() {
        CommercioDoc.Metadata.Schema(
            uri = "http://example.com/",
            version = invalid33CharsString
        )
    }

    @Test
    fun `convertValue from map to CommercioDoc_Metadata_Schema should behave correctly`() {
        val json = mutableMapOf<String, String>()
        json["uri"] = correctCommercioDocMetadataSchema.uri
        json["version"] = correctCommercioDocMetadataSchema.version
        assertEquals(
            correctCommercioDocMetadataSchema,
            jacksonObjectMapper().convertValue(json, CommercioDoc.Metadata.Schema::class.java)
        )
    }


    // CHECKSUM

    @Test
    fun `convertValue from map to CommercioDoc_Checksum should behave correctly`() {
        val json = mutableMapOf<String, Any>()
        json["algorithm"] = "md5"
        json["value"] = correctCommercioDocChecksum.value

        assertEquals(
            correctCommercioDocChecksum,
            jacksonObjectMapper().convertValue(json, CommercioDoc.Checksum::class.java)
        )
    }


    // ENCRYPTIONDATA.KEY

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc_EncryptionData_Key should throw an IllegalArgumentException because encryption_data_keys_  _value must have a valid length  (less than 512 bytes) )`() {
        CommercioDoc.EncryptionData.Key(
            recipientDid = correctDid,
            value = invalid520CharactersString
        )
    }

    @Test
    fun `convertValue from map to CommercioDoc_EncryptionData_Key should behave correctly`() {
        val json = mutableMapOf<String, Any>()
        json["recipient"] = correctCommercioDocEncryptionDataKey.recipientDid
        json["value"] = correctCommercioDocEncryptionDataKey.value

        assertEquals(
            correctCommercioDocEncryptionDataKey,
            jacksonObjectMapper().convertValue(json, CommercioDoc.EncryptionData.Key::class.java)
        )
    }

    // DOSIGN

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc_DoSign should throw an IllegalArgumentException because vcrId length should be less than 64 bytes )`() {
        CommercioDoc.CommercioDoSign(
            storageUri = "http=//do.sign",
            signerIstance = "signer",
            vcrId = invalid65CharsString,
            certificateProfile = "profile"
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `CommercioDoc_DoSign should throw an IllegalArgumentException because certificateProfile length should be less than 32 bytes )`() {
        CommercioDoc.CommercioDoSign(
            storageUri = "http=//do.sign",
            signerIstance = "signer",
            vcrId = "",
            certificateProfile = invalid33CharsString
        )
    }

    @Test
    fun `convertValue from map to CommercioDoc_DoSign should behave correctly`() {
        val minimalJson = mutableMapOf<String, String>()
        minimalJson["storage_uri"] = correctCommercioDoSign.storageUri
        minimalJson["signer_instance"] = correctCommercioDoSign.signerIstance
        minimalJson["vcr_id"] = correctCommercioDoSign.vcrId
        minimalJson["certificate_profile"] = correctCommercioDoSign.certificateProfile

        val minimalCommercioDoSign = CommercioDoc.CommercioDoSign(
            storageUri = correctCommercioDoSign.storageUri,
            signerIstance = correctCommercioDoSign.signerIstance,
            vcrId = correctCommercioDoSign.vcrId,
            certificateProfile = correctCommercioDoSign.certificateProfile
        )

        assertEquals(
            minimalCommercioDoSign,
            jacksonObjectMapper().convertValue(minimalJson, CommercioDoc.CommercioDoSign::class.java)
        )
    }
}
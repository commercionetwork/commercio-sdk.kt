package network.commercio.sdk.entities.docs.legacy.`21`

import network.commercio.sdk.entities.docs.CommercioDoc
import network.commercio.sdk.entities.docs.legacy.`21`.CommercioDoc as CommercioDocLegacy

/**
 * Converts the provided [commercioDoc] and returns a [legacy.CommercioDoc]
 * version compatible with a 2.1 chain.
 */
class CommercioDocMapper {

    fun toLegacy(commercioDoc: CommercioDoc): CommercioDocLegacy {
        var schema: CommercioDocLegacy.Metadata.Schema? = null

        if (commercioDoc.metadata.schema != null) {
            schema = CommercioDocLegacy.Metadata.Schema(
                uri = commercioDoc.metadata.schema.uri,
                version = commercioDoc.metadata.schema.version
            )
        }


        val metadata = CommercioDocLegacy.Metadata(
            contentUri = commercioDoc.metadata.contentUri,
            schema = schema,
            schemaType = commercioDoc.metadata.schemaType
        )

        var checksum: CommercioDocLegacy.Checksum? = null

        if (commercioDoc.checksum != null) {
            checksum = CommercioDocLegacy.Checksum(
                algorithm = _checksumAlgorithmToLegacy(commercioDoc.checksum.algorithm),
                value = commercioDoc.checksum.value
            )
        }

        var doSign: CommercioDocLegacy.CommercioDoSign? = null

        if (commercioDoc.doSign != null) {
            doSign = CommercioDocLegacy.CommercioDoSign(
                certificateProfile = commercioDoc.doSign.certificateProfile,
                signerIstance = commercioDoc.doSign.signerIstance,
                storageUri = commercioDoc.doSign.storageUri,
                vcrId = commercioDoc.doSign.vcrId,
                sdnData = commercioDoc.doSign.sdnData?.map { _sdnDataToLegacy(it) }?.toList()
            )
        }

        var encryptionData: CommercioDocLegacy.EncryptionData? = null

        if (commercioDoc.encryptionData != null) {
            encryptionData = CommercioDocLegacy.EncryptionData(
                encryptedData = commercioDoc.encryptionData.encryptedData.map { it.toString() }?.toList(),
                keys = commercioDoc.encryptionData.keys.map { _encryptionDataKeyToLegacy(it) }?.toList()
            )
        }

        return CommercioDocLegacy(
            senderDid = commercioDoc.senderDid,
            recipientsDids = commercioDoc.recipientsDids,
            uuid = commercioDoc.uuid,
            metadata = metadata,
            contentUri = commercioDoc.contentUri,
            checksum = checksum,
            doSign = doSign,
            encryptionData = encryptionData
        )
    }

    fun _checksumAlgorithmToLegacy(algorithm: CommercioDoc.Checksum.Algorithm): CommercioDocLegacy.Checksum.Algorithm {
        return when (algorithm) {
            CommercioDoc.Checksum.Algorithm.MD5 ->
                CommercioDocLegacy.Checksum.Algorithm.MD5
            CommercioDoc.Checksum.Algorithm.SHA1 ->
                CommercioDocLegacy.Checksum.Algorithm.SHA1
            CommercioDoc.Checksum.Algorithm.SHA224 ->
                CommercioDocLegacy.Checksum.Algorithm.SHA224
            CommercioDoc.Checksum.Algorithm.SHA256 ->
                CommercioDocLegacy.Checksum.Algorithm.SHA256
            CommercioDoc.Checksum.Algorithm.SHA384 ->
                CommercioDocLegacy.Checksum.Algorithm.SHA384
            CommercioDoc.Checksum.Algorithm.SHA512 ->
                CommercioDocLegacy.Checksum.Algorithm.SHA512
            else -> throw Exception("Unsupported algorithm $algorithm")
        }
    }


    fun _sdnDataToLegacy(sdnData: CommercioDoc.CommercioDoSign.CommercioSdnData): CommercioDocLegacy.CommercioDoSign.CommercioSdnData {
        return when (sdnData) {
            CommercioDoc.CommercioDoSign.CommercioSdnData.COMMON_NAME ->
                CommercioDocLegacy.CommercioDoSign.CommercioSdnData.COMMON_NAME
            CommercioDoc.CommercioDoSign.CommercioSdnData.COUNTRY ->
                CommercioDocLegacy.CommercioDoSign.CommercioSdnData.COUNTRY
            CommercioDoc.CommercioDoSign.CommercioSdnData.GIVEN_NAME ->
                CommercioDocLegacy.CommercioDoSign.CommercioSdnData.GIVEN_NAME
            CommercioDoc.CommercioDoSign.CommercioSdnData.ORGANIZATION ->
                CommercioDocLegacy.CommercioDoSign.CommercioSdnData.ORGANIZATION
            CommercioDoc.CommercioDoSign.CommercioSdnData.SERIAL_NUMBER ->
                CommercioDocLegacy.CommercioDoSign.CommercioSdnData.SERIAL_NUMBER
            CommercioDoc.CommercioDoSign.CommercioSdnData.SURNAME ->
                CommercioDocLegacy.CommercioDoSign.CommercioSdnData.SURNAME
            else -> throw Exception("Unsupported SdnData $sdnData")
        }
    }

    fun _encryptionDataKeyToLegacy(dataKey: CommercioDoc.EncryptionData.Key): CommercioDocLegacy.EncryptionData.Key {
        return CommercioDocLegacy.EncryptionData.Key(
            recipientDid = dataKey.recipientDid,
            value = dataKey.value
        )
    }

}
package network.commercio.sdk.crypto


import org.bouncycastle.asn1.x500.X500NameBuilder
import org.bouncycastle.asn1.x500.style.BCStyle
import org.bouncycastle.asn1.x509.BasicConstraints
import org.bouncycastle.asn1.x509.Extension
import org.bouncycastle.asn1.x509.KeyUsage
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.jcajce.JcaPEMWriter
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder
import java.io.StringWriter
import java.math.BigInteger
import java.security.KeyPair
import java.security.cert.X509Certificate
import java.util.*

/**
 * Allows to create an x509 certificate from wallet address and key pair.
 */
object CertificateHelper {

    /**
     * Creates an X509 certificate using the given [keyPair] and [walletAddress].
     * @param walletAddress bech32Address of the user wallet that will be used as certificate issuer and subject.
     * @param keyPair pair of keys that will be used to sign and build the certificate.
     * @param signatureAlgorithm identifies the algorithm with which the signature will be done.
     */
    fun x509certificateFromWallet(walletAddress: String, keyPair: KeyPair, signatureAlgorithm: String = "SHA256withRSA"): X509Certificate {
        val creationDate = Date()
        val serial = BigInteger.valueOf(Random().nextLong())

        val expirationDate = Calendar.getInstance()
            .apply { add(Calendar.YEAR, 1) }
            .time

        val issuer = X500NameBuilder().apply {
            addRDN(BCStyle.GIVENNAME, walletAddress)
        }.build()

        val certificateBuilder = JcaX509v3CertificateBuilder(
            issuer,
            serial,
            creationDate,
            expirationDate,
            issuer,
            keyPair.public
        )

        val extensionUtils = JcaX509ExtensionUtils()

        //Add the required x509 extensions
        certificateBuilder.addExtension(
            Extension.subjectKeyIdentifier,
            false,
            extensionUtils.createSubjectKeyIdentifier(keyPair.public)
        )

        certificateBuilder.addExtension(
            Extension.basicConstraints,
            false,
            BasicConstraints(false)
        )

        certificateBuilder.addExtension(
            Extension.keyUsage,
            true,
            KeyUsage(KeyUsage.nonRepudiation)
        )

        //Define who is the authority signing the certificate
        val signer = JcaContentSignerBuilder(signatureAlgorithm)
            .setProvider(BouncyCastleProvider())
            .build(keyPair.private)


        //Define the holder of the certificate
        val holder = certificateBuilder.build(signer)

        return JcaX509CertificateConverter().getCertificate(holder)
    }


    /**
     * Return the PEM representation of the given [certificate].
     */
    fun getPem(certificate: X509Certificate): String {
        val writer =    StringWriter()
        val pWriter =   JcaPEMWriter(writer)
        pWriter.writeObject(certificate)
        pWriter.flush()
        return writer.toString()
    }
}

package network.commercio.sdk.entities.id

import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.Wallet
import network.commercio.sdk.crypto.KeysHelper
import network.commercio.sdk.id.DidDocumentHelper
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MsgSetDidDocumentTests {

    @Test
    fun `MsgSetDidDocument is properly JSON encoded`() {
        val mnemonic = listOf(
            "dash",
            "ordinary",
            "anxiety",
            "zone",
            "slot",
            "rail",
            "flavor",
            "tortoise",
            "guilt",
            "divert",
            "pet",
            "sound",
            "ostrich",
            "increase",
            "resist",
            "short",
            "ship",
            "lift",
            "town",
            "ice",
            "split",
            "payment",
            "round",
            "apology"
        )
        val chain = NetworkInfo(bech32Hrp = "did:com:", lcdUrl = "")
        val wallet = Wallet.derive(mnemonic = mnemonic, networkInfo = chain)
        val keys = KeysHelper.generateRsaKeyPair()
        val didDocument = DidDocumentHelper.fromWallet(wallet, listOf(keys.publicWrapper,keys.publicWrapper))
        val msg = MsgSetDidDocument(didDocument)
        assertNotNull(msg.value)
    }
}
package network.commercio.sdk.kyc

import kotlinx.coroutines.runBlocking
import network.commercio.sacco.NetworkInfo
import network.commercio.sacco.Wallet
import network.commercio.sdk.entities.id.Did
import network.commercio.sdk.entities.kyc.InviteUser
import org.junit.Assert
import org.junit.Test
import org.kethereum.bip39.generateMnemonic
import org.kethereum.bip39.wordlists.WORDLIST_ENGLISH


class InviteUserHelperTest {
    @Test
    fun `InviteUserHelper fromWallet create correctly InviteUser`() = runBlocking {
        val networkInfo = NetworkInfo(bech32Hrp = "did:com:", lcdUrl = "")

        val mnemonicString =
            "dash ordinary anxiety zone slot rail flavor tortoise guilt divert pet sound ostrich increase resist short ship lift town ice split payment round apology"
        val mnemonic = mnemonicString.split(" ")
        val wallet = Wallet.derive(mnemonic, networkInfo)

        val newUserMnemonic = generateMnemonic(strength = 256, wordList = WORDLIST_ENGLISH).split(" ")
        val newUserWallet = Wallet.derive(newUserMnemonic, networkInfo)


        val expectedInviteUser = InviteUser(
            recipientDid = newUserWallet.bech32Address,
            senderDid = wallet.bech32Address
        )

        val inviteUser = InviteUserHelper.fromWallet(
            wallet = wallet,
            recipientDid = Did(newUserWallet.bech32Address)
        )

        Assert.assertEquals(expectedInviteUser.toString(), inviteUser.toString())
    }
}
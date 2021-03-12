package network.commercio.sdk.utils

import org.junit.Assert
import org.junit.Test


class matchBech32FormatTest {

    @Test
    fun `String lenght not between 8 and 90 should return false`() {
        val str100 =
            "xRqpfSGKVF8Qor1HcKhy2gwmUw0fSZ25IXp7y2DdJctxTyqsiP4YruHIWjY2Qkhe8aZxag9Smi3NwMrFs5CPYzkUF3P4sCOYmlRI"
        Assert.assertEquals(matchBech32Format(""), false)
        Assert.assertEquals(matchBech32Format(str100), false)
    }

    @Test
    fun `Bech32 with -b-, -i- or -o- after the 1 should return false`() {
        Assert.assertEquals(matchBech32Format("did:com:1b"), false)
        Assert.assertEquals(matchBech32Format("did:com:1i"), false)
        Assert.assertEquals(matchBech32Format("did:com:1o"), false)
    }

    @Test
    fun `Bech32 without the -1- as separator should return false`() {
        Assert.assertEquals(matchBech32Format("did:com:acdefg"), false)
    }

    @Test
    fun `Bech32 without the hrp part before -1- should return false`() {
        Assert.assertEquals(matchBech32Format("1acdefg"), false)
    }

    @Test
    fun `Valid Bech32 should return true`() {
        Assert.assertEquals(matchBech32Format("did:com:1acdefg"), true)
        Assert.assertEquals(matchBech32Format("did:com:1mfddzjvr8vpeqdtm6fuay2nvnecuk9qa8usqq5"), true)
        Assert.assertEquals(matchBech32Format("cosmos1acdefg"), true)
    }
}
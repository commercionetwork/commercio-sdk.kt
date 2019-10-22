package network.commercio.sdk.cyrpto

import network.commercio.sdk.crypto.KeysHelper
import org.bouncycastle.util.encoders.Hex
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Tests for [KeysHelper].
 */
class KeysHelperTests {

    @Test
    fun `generateAesKey generates random keys`() {
        val keys = (1..100).map { KeysHelper.generateAesKey() }.map { Hex.toHexString(it.encoded) }.distinct()
        assertEquals(100, keys.size)
    }

    @Test
    fun `generateRsaKeyPair generates random keys`() {
        val keys = (1..100).map { KeysHelper.generateRsaKeyPair() }.map { Hex.toHexString(it.private.encoded) }.distinct()
        assertEquals(100, keys.size)
    }
}
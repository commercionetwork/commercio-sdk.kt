package network.commercio.sdk.utils

import org.junit.Assert
import org.junit.Test


class GetStringBytesTest {
    @Test
    fun `getStringBytes with null value retun 0 `() {
        Assert.assertEquals(getStringBytes(null), 0)
    }

    @Test
    fun `getStringBytes with empty value retun 0 `() {
        Assert.assertEquals(getStringBytes(""), 0)
    }

    @Test
    fun `getStringBytes should work properly with Italian characters`() {
        Assert.assertEquals(getStringBytes("òèè"), 6)
    }

    @Test
    fun `getStringBytes should work properly with Chinese characters`() {
        Assert.assertEquals(getStringBytes("豆票维"), 9)
    }
}
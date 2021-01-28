package network.commercio.sdk.utils

import org.junit.Assert
import org.junit.Test

class MatchUuidv4Test {

    @Test
    fun `Empty uuid should return false`() {
        Assert.assertEquals(matchUuidv4(""), false)
    }

    @Test
    fun `Invalid uuid should return false`() {
        Assert.assertEquals(matchUuidv4("690f3bf4-5fbf-11eb-ae93-0242ac130002"), false)
        Assert.assertEquals(matchUuidv4("6ba7b810-9dad-11d1-80b4-00c04fd430c8"), false)
        Assert.assertEquals(matchUuidv4("00000000-0000-0000-0000-000000000000"), false)
    }

    @Test
    fun `Valid uuid v4 should return true`() {
        Assert.assertEquals(matchUuidv4("e21fd7bf-fd58-40ca-be2d-f50d29189276"), true)
    }
}
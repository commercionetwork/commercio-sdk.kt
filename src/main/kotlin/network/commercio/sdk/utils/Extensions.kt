package network.commercio.sdk.utils

import org.bouncycastle.util.encoders.Hex
import java.text.SimpleDateFormat
import java.util.*

/**
 * Tries to execute the given [block], returning its result or `null` if some exception was
 * raised.
 */
internal suspend fun <T> tryOrNull(block: suspend () -> T): T? {
    return try {
        block()
    } catch (t: Throwable) {
        println(t)
        null
    }
}

/**
 * Converts the given [ByteArray] into its hexadecimal representation.
 */
fun ByteArray.toHex(): String {
    return Hex.toHexString(this)
}

fun String.readHex(): ByteArray {
    return Hex.decode(this)
}

fun getTimeStamp(): String {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }.format(Date())
}
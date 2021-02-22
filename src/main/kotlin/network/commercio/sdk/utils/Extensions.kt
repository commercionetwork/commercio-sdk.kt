package network.commercio.sdk.utils

import org.bouncycastle.util.encoders.Hex
import java.text.SimpleDateFormat
import java.util.*

/**
 * Tries to execute the given [block], returning its result or `null` if some exception was
 * raised.
 */
suspend fun <T> tryOrNull(block: suspend () -> T): T? {
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

/**
 * Returns the number of bytes of a UTF-8 encoded [str]. If [str] is null then 0 is returned.
 */
fun getStringBytes(str: String?): Int {
    if (str.isNullOrEmpty()) {
        return 0
    }
    val byteArray = str.toByteArray(Charsets.UTF_8)
    return byteArray.size
}

/**
 * Returns true if a string [uuid] has a Uuid-v4 format, false otherwise.
 */
fun matchUuidv4(uuid: String): Boolean {
    val regex =
        "^[0-9A-F]{8}-[0-9A-F]{4}-4[0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}$".toRegex(setOf(RegexOption.IGNORE_CASE))
    return regex.matches(uuid)
}

/**
 * Returns true if the provided [str] is a valid bech32 string, false otherwise.
 * Use only a basic regex.
 * In progress: missing checksum check.
 */
fun matchBech32Format(str: String): Boolean {
    if (str.length < 8 || str.length > 90) {
        return false;
    }
    val regex = "^(\\S{1,83})(1)([^1bio]{6,88})\$".toRegex(setOf(RegexOption.IGNORE_CASE))
    return regex.matches(str)
}

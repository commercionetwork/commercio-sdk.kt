package network.commercio.sdk.utils

import org.spongycastle.util.encoders.Hex

suspend fun <T> tryOrNull(block: suspend () -> T): T? {
    return try {
        block()
    } catch (t: Throwable) {
        null
    }
}

fun ByteArray.toHex(): String {
    return Hex.toHexString(this)
}
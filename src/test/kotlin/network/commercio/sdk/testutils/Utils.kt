package network.commercio.sdk.testutils

fun Any.readResource(resource: String): String {
    return javaClass.classLoader?.getResource(resource)?.readText() ?: ""
}
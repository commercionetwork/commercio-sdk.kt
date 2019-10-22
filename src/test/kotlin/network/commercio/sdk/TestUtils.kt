package network.commercio.sdk

import java.io.File

/**
 * Searches inside the `resources` folder for the given [resource] returning a [File] reference if found.
 * @throws IllegalStateException when the given [resource] is not found.
 */
fun Any.readResource(resource: String): String {
    val path = File(javaClass.classLoader.getResource(resource).file).parent

    val file = when (resource.contains("/")) {
        true -> {
            val splitPath = resource.split("/")
            val fileName = splitPath[splitPath.size - 1]
            File(path, fileName)
        }
        false -> File(path, resource)
    }

    val newPath = file.absolutePath.replace("%20", " ")
    return File(newPath).readText()
}
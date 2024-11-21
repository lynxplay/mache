package io.papermc.mache

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.nio.file.Files
import java.nio.file.Path
import java.util.UUID
import kotlin.io.path.exists

data class Context(val properties: Map<String, Any?>) {
    fun username(): String = properties["mache.username"] as? String ?: "Player"
    fun uuid(): UUID = (properties["mache.uuid"] as? String).let { UUID.fromString(it) } ?: UUID.randomUUID()
    fun uuidAsStr(): String = uuid().toString().replace("-", "")
}

fun extractAuthToken(properties: Context): String? = listOf(
    // Flatpak installation of prism. The only true installation.
    fromPrismLauncher(
        Path.of(
            System.getProperty("user.home"),
            ".var/app/org.prismlauncher.PrismLauncher/data/PrismLauncher/accounts.json"
        )
    ),
    // Fallback to property
    ::fromProperties,
).firstNotNullOfOrNull { it(properties) }

fun fromProperties(context: Context): String? = context.properties["mache.token"] as? String

fun fromPrismLauncher(path: Path): (Context) -> String? {
    return { c -> fromPrismLauncher(path, c) }
}

fun fromPrismLauncher(path: Path, context: Context): String? {
    if (!path.exists()) return ""

    val decodeFromString = Json.decodeFromString<JsonElement>(Files.readString(path))

    return decodeFromString
        .jsonObject["accounts"]
        ?.jsonArray
        ?.filter {
            it.jsonObject["profile"]?.jsonObject?.get("id")?.jsonPrimitive?.content == context.uuidAsStr()
                    && it.jsonObject["type"]?.jsonPrimitive?.content == "MSA"
        }?.map {
            it.jsonObject["ygg"]?.jsonObject?.get("token")?.jsonPrimitive?.content
        }?.firstOrNull()
}
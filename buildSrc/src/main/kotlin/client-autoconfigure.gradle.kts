import io.papermc.mache.Context
import io.papermc.mache.extractAuthToken
import io.papermc.sculptor.shared.MacheExtension

plugins {
    id("io.papermc.sculptor.version")
}

configure<MacheExtension> {
    runClient {
        val context = Context(properties)
        extraArgs = project.providers.provider {
            listOf(
                "--username", context.username(),
                "--uuid", context.uuidAsStr(),
                "--userType", "MSA",
            )
        }
        accessTokenArg.set(
            project.providers.provider { extractAuthToken(context) }.orElse("none")
        )
    }
}
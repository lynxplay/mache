import io.papermc.sculptor.shared.util.MinecraftJarType

plugins {
    id("io.papermc.sculptor.version") version "1.0.8"
}

val generateReportsProperty = providers.gradleProperty("generateReports")
mache {
    minecraftVersion = "1.21.1"

    repositories.register("sonatype snapshots") {
        url = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
        includeGroups.add("org.vineflower")
    }

    val args = mutableListOf(
        "--temp-dir={tempDir}",
        "--remapper-file={remapperFile}",
        "--mappings-file={mappingsFile}",
        "--params-file={paramsFile}",
        // "--constants-file={constantsFile}",
        "--output={output}",
        "--input={input}",
        "--input-classpath={inputClasspath}",
    )
    if (generateReportsProperty.getOrElse("false").toBooleanStrict()) {
        args.addAll(listOf(
            "--reports-dir={reportsDir}",
            "--all-reports",
        ))
    }

    minecraftJarType = MinecraftJarType.CLIENT

    remapperArgs.set(args)
}

dependencies {
    codebook("1.0.10")
    remapper(art("1.0.14"))
    decompiler(vineflower("1.11.0-20240829.184116-48"))
    parchment("1.21", "2024.07.28")
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.0.1")
}

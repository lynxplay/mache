import io.papermc.sculptor.shared.util.MinecraftJarType

plugins {
    id("client-autoconfigure")
}

val generateReportsProperty = providers.gradleProperty("generateReports")
mache {
    minecraftVersion = "26.1.2"
    minecraftJarType = MinecraftJarType.CLIENT

    val args = mutableListOf(
        "--temp-dir={tempDir}",
        "--unpick-file={constantsFile}",
        "--output={output}",
        "--input={input}",
        "--input-classpath={inputClasspath}",
        "--hypo-parallelism=1",
    )
    if (generateReportsProperty.getOrElse("false").toBooleanStrict()) {
        args.addAll(listOf(
            "--reports-dir={reportsDir}",
            "--all-reports",
        ))
    }

    codebookArgs = args
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-parameters")
    options.forkOptions.memoryMaximumSize = "1G"
}

dependencies {
    codebook("2.0.0-SNAPSHOT")
    decompiler(vineflower("1.11.2"))
    constants("io.papermc.unpick-definitions:unpick-definitions:26.1+build.2")
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.0.1")
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
    compileOnly("org.checkerframework:checker-qual:3.49.0")
}

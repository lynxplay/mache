plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-releases/") {
        mavenContent { releasesOnly() }
    }
}

dependencies {
    implementation("io.papermc.sculptor:sculptor-version:1.0.15")
}
plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-releases/") {
        mavenContent { releasesOnly() }
    }
    maven("https://repo.papermc.io/repository/maven-snapshots/") {
        mavenContent { snapshotsOnly() }
    }
}

dependencies {
    implementation("io.papermc.sculptor:sculptor-version:2.0.0-SNAPSHOT")
}
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("com.google.cloud.tools.jib") version Versions.JIB
}

repositories {
    maven("https://repo.perfectdreams.net/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://m2.dv8tion.net/releases")
    mavenLocal()
}

dependencies {
    implementation(project(":common"))
    implementation(project(":commands"))
    implementation(project(":services:memory"))
    implementation(project(":services:pudding"))
    implementation(project(":platforms:discord:common"))
    implementation(project(":platforms:discord:commands"))

    implementation("net.perfectdreams.discordinteraktions:webserver-ktor-kord:0.0.7-SNAPSHOT")
    implementation("io.ktor:ktor-server-netty:1.6.0")

    // Sequins
    api("net.perfectdreams.sequins.ktor:base-route:1.0.2")

    // Prometheus
    api("io.prometheus:simpleclient:0.10.0")
    api("io.prometheus:simpleclient_hotspot:0.10.0")
    api("io.prometheus:simpleclient_common:0.10.0")

    implementation("dev.kord:kord-rest:0.8.x-SNAPSHOT")

    // Required for tests, if this is missing then Gradle will throw
    // "No tests found for given includes: [***Test](filter.includeTestsMatching)"
    implementation(kotlin("test"))
    implementation(kotlin("test-junit"))
    implementation("org.junit.jupiter:junit-jupiter:5.4.2")
    implementation("org.assertj:assertj-core:3.19.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = Versions.JVM_TARGET
}

jib {
    container {
        ports = listOf("8080")
    }

    to {
        image = "ghcr.io/lorittabot/cinnamon-discord-interaktions"

        auth {
            username = System.getProperty("DOCKER_USERNAME") ?: System.getenv("DOCKER_USERNAME")
            password = System.getProperty("DOCKER_PASSWORD") ?: System.getenv("DOCKER_PASSWORD")
        }
    }

    from {
        image = "openjdk:15.0.2-slim-buster"
    }
}

tasks.withType<ShadowJar> {
    manifest {
        attributes["Main-Class"] = "net.perfectdreams.loritta.platform.interaktions.LorittaInteraKTionsLauncher"
    }
}

tasks {
    processResources {
        from("../../../resources/") // Include folders from the resources root folder
    }

    build {
        dependsOn(shadowJar)
    }
}
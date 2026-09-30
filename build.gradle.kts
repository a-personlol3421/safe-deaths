plugins {
    id("java")
    id("xyz.jpenilla.run-paper") version "3.1.0"
}

group = "io.github.aperson.safedeaths"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.2.build.+")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

tasks {
    runServer {
        minecraftVersion("26.2")

        jvmArgs("-Dcom.mojang.eula.agree=true")
    }
}
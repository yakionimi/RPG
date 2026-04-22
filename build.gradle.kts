plugins {
    id("fabric-loom") version "1.6-SNAPSHOT"
    id("maven-publish")
}

group = "com.yaki"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://maven.fabricmc.net/")
}

dependencies {
    minecraft("com.mojang:minecraft:1.20.1")
    mappings("net.fabricmc:yarn:1.20.1+build.10:v2")
    modImplementation("net.fabricmc:fabric-loader:0.16.10")

    modImplementation("net.fabricmc.fabric-api:fabric-api:0.92.7+1.20.1")
}

tasks.withType<JavaCompile> {
    options.release.set(17)
}
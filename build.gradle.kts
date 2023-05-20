plugins {
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("java")
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    implementation("net.dv8tion:JDA:5.0.0-beta.9")
    implementation("club.minnced:discord-webhooks:0.8.2")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

application {
    mainClass.set("edu.berkeley.bmt.minecraft_discord_bridge.Plugin")
}

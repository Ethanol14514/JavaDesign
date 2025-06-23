import org.gradle.api.internal.plugins.MainClass

plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("application")
}

group = "org.teamethanol.bookManager"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // https://mvnrepository.com/artifact/com.formdev/flatlaf
    implementation("com.formdev:flatlaf:3.6")
    // https://mvnrepository.com/artifact/com.formdev/flatlaf-intellij-themes
    implementation("com.formdev:flatlaf-intellij-themes:3.6")
    // https://mvnrepository.com/artifact/com.miglayout/miglayout-swing
    implementation("com.miglayout:miglayout-swing:11.4.2")
    // https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
    implementation("org.xerial:sqlite-jdbc:3.50.1.0")
}

tasks.test {
    useJUnitPlatform()
}

application{
    mainClass.set("org.teamethanol.bookManager.Main")
}
import com.github.lamba92.gradle.utils.*

buildscript {
    repositories {
        maven("https://dl.bintray.com/lamba92/com.github.lamba92")
        google()
    }
    dependencies {
        classpath("com.github.lamba92", "lamba-gradle-utils", "1.0.6")
    }
}

plugins {
    kotlin("jvm") version "1.3.72"
    id("com.jfrog.bintray") version "1.8.5"
    `maven-publish`
}

repositories {
    mavenCentral()
    jcenter()
}

group = "com.github.lamba92"
version = TRAVIS_TAG ?: "1.1.5"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains", "annotations", "15.0")
    implementation("net.sf.kxml", "kxml2", "2.3.0")
    implementation("com.squareup.okhttp3", "okhttp", "3.11.0")
    implementation("com.burgstaller", "okhttp-digest", "1.18")
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.3.4")
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets["main"].allSource)
}

publishing.publications.register<MavenPublication>("mavenJava") {
    from(components["java"])
    artifact(sourcesJar)
}

prepareForPublication()

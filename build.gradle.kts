plugins {
    kotlin("jvm") version "1.3.72"
    id("com.jfrog.bintray") version "1.8.5"
    `maven-publish`
    publishing
    maven
}

repositories {
    mavenCentral()
    jcenter()
}

group = "be.teletask.onvif"
version = "1.1.5"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains", "annotations", "15.0")
    implementation("net.sf.kxml", "kxml2", "2.3.0")
    implementation("com.squareup.okhttp3", "okhttp", "3.11.0")
    implementation("com.burgstaller", "okhttp-digest", "1.18")
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.3.4")
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

bintray {
    user = System.getenv("bintrayUser")
    key = System.getenv("bintrayApiKey")
    setPublications("mavenJava")
    with(pkg) {
        repo = "maven"
        name = "onvif-java"
        desc = "ONVIF client library for Java and Kotlin"
        userOrg = "szantogab"
        setLicenses("MIT")
        vcsUrl = "https://github.com/szantogab/ONVIF-Java"
        with(version) {
            name = project.version.toString()
        }
    }
}

val sourcesJar by tasks.creating(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allSource)
}

artifacts.add("archives", sourcesJar)

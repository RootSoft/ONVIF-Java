plugins {
    kotlin("jvm") version "1.3.21"
    maven
}

repositories {
    mavenCentral()
    jcenter()
}

group = "be.teletask.onvif"
version = "1.0.3"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains", "annotations", "15.0")
    implementation("net.sf.kxml", "kxml2", "2.3.0")
    implementation("com.squareup.okhttp3", "okhttp", "3.11.0")
    implementation("com.burgstaller", "okhttp-digest", "1.18")
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.1.1")
}

val sourcesJar by tasks.creating(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allSource)
}

artifacts.add("archives", sourcesJar)
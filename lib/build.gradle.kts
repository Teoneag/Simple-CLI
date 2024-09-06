plugins {
    `java-library`
    `maven-publish`
}

group = "com.teoneag"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    api(libs.commons.math3)

    implementation(libs.guava)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifactId = "simple-cli"
        }
    }
    repositories {
        mavenLocal()
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

import org.jreleaser.model.Active

plugins {
    java
    `java-library`
    `maven-publish`
    signing
    id("org.jreleaser") version "1.14.0"
}

group = "io.github.teoneag"
version = "1.3"

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
        languageVersion = JavaLanguageVersion.of(22)
    }
    withSourcesJar() // generates sources.jar
    withJavadocJar() // generates javadoc.jar
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "simple-cli"

            from(components["java"])

            artifact(tasks.named("sourcesJar"))
            artifact(tasks.named("javadocJar"))

            pom {
                name = "Simple CLI for Java"
                description = "A simple library for an interactive CLI"
                url = "https://github.com/Teoneag/Simple-CLI-for-Java"
                licenses {
                    license {
                        name = "The Apache License, Version 2.0"
                        url = "http://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "teoneag"
                        name = "Teodor Neagoe"
                        email = "teodor.neagoe.climber@gmail.com"
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/Teoneag/Simple-CLI-for-Java.git")
                    developerConnection.set("scm:git:ssh://github.com/Teoneag/Simple-CLI-for-Java.git")
                    url.set("https://github.com/Teoneag/Simple-CLI-for-Java")
                }
            }
        }
    }
    repositories {
        maven {
            url = layout.buildDirectory.dir("staging-deploy").get().asFile.toURI()
        }
    }

}

signing {
    useGpgCmd() // Use GPG command line for signing
    sign(publishing.publications) // Sign all publications defined in the publishing block
}

jreleaser {
    signing {
        active = Active.ALWAYS
        armored = true
    }
    deploy {
        maven {
            nexus2 {
                create("maven-central") {
                    active = Active.ALWAYS
                    url = "https://central.sonatype.com/api/v1/publisher"
//                    snapshotUrl = "https://aws.oss.sonatype.org/content/repositories/snapshots"
                    closeRepository.set(true)
                    releaseRepository.set(true)
                    stagingRepositories.add("target/staging-deploy")
                }
            }
        }
    }
}


tasks.test {
    useJUnitPlatform()
}
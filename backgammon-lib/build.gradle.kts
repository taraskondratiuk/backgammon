plugins {
    kotlin("multiplatform") version "1.4.32"
    id("io.github.gciatto.kt-npm-publish") version "0.3.6"
    id("maven-publish")
}

group = "me.taras"
version = "0.1.19"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        withJava()
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }
    }
    js {
        nodejs ()
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }

    npmPublishing {
        token.set("9fdad2bb-00b0-4367-aa9f-d2a834521346")
    }

    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/taraskondratiuk/backgammon")
                credentials {
                    username = "taraskondratiuk"
                    password = "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"
                }
            }
        }
    }
}

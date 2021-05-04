plugins {
    kotlin("multiplatform") version "1.4.32"
    id("io.github.gciatto.kt-npm-publish") version "0.3.6"
    id("maven-publish")
}

group = "me.taras"
version = "0.1.0"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
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
        with(token) {
            set(System.getenv("NPM_TOKEN"))
        }
    }

    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/${System.getenv("ORGANIZATION")}/${System.getenv("REPO_NAME")}")
                credentials {
                    username = System.getenv("GITHUB_USERNAME")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}

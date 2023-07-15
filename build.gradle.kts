plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    java
}

group = "fr.amoya"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks {
    kotlin {
        jvm {
            withJava()
            compilations.all {
                kotlinOptions.jvmTarget = "11"
            }
        }
        val hostOs = System.getProperty("os.name")
        val isMingwX64 = hostOs.startsWith("Windows")
        when {
            hostOs == "Mac OS X" -> macosX64("native")
            hostOs == "Linux" -> linuxX64("native")
            isMingwX64 -> mingwX64("native")
            else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
        }

        sourceSets {
            val commonMain by getting {
                dependencies {
                    implementation(kotlin("stdlib"))
                    implementation(kotlin("stdlib-common"))
                    implementation(kotlin("reflect"))

                    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
                    implementation("com.soywiz.korlibs.korio:korio:4.0.2")
                }
            }
        }
    }
}

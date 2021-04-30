plugins {
  kotlin("multiplatform") version "1.4.32"
  java
}

group = "fr.amoya"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
  jcenter()
}

dependencies {
  implementation("org.junit.jupiter:junit-jupiter:5.7.0")
  implementation("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}


tasks {
  // Tests
  withType<Test> {
    useJUnitPlatform()
  }
}

kotlin {
  targets.all {
    compilations.all {
      kotlinOptions {
        allWarningsAsErrors = true
      }
    }
  }

  jvm {
    withJava()
    compilations.all {
      kotlinOptions.jvmTarget = "1.8"
    }
    testRuns["test"].executionTask.configure {
      useJUnitPlatform()
    }
  }
  val hostOs = System.getProperty("os.name")
  val isMingwX64 = hostOs.startsWith("Windows")
  val nativeTarget = when
  {
    hostOs == "Mac OS X" -> macosX64("native")
    hostOs == "Linux"    -> linuxX64("native")
    isMingwX64           -> mingwX64("native")
    else                 -> throw GradleException("Host OS is not supported in Kotlin/Native.")
  }


  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(kotlin("stdlib"))
        implementation(kotlin("stdlib-common"))
        implementation(kotlin("reflect"))
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
        implementation(kotlin("test-junit"))
      }
    }
    val jvmMain by getting
    val jvmTest by getting {
      dependencies {
        implementation(kotlin("test"))
        implementation(kotlin("test-junit"))
      }
    }
    val nativeMain by getting
    val nativeTest by getting
  }
}

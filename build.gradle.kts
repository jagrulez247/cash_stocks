buildscript {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }

    dependencies {
        classpath(Plugins.gradleBuildTools)
        classpath(Plugins.kotlinPlugin)
        classpath(Plugins.googleServices)
        classpath(Plugins.hiltPlugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
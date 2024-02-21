plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
}

group = "suhov.vitaly"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //Почта
    implementation("com.sun.mail:javax.mail:1.6.2")
    //Сериализация/десереализация
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    //Асинхронщина
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    //Парсер HTML -> Text
    implementation ("org.jsoup:jsoup:1.15.3")
}

kotlin {
    jvmToolchain(20)
}
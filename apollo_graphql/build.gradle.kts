import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    id("com.apollographql.apollo").version("2.2.0")
}

group = "org.example"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
    // apollo
    implementation ("com.apollographql.apollo:apollo-runtime:2.2.0")
    implementation ("com.apollographql.apollo:apollo-coroutines-support:2.2.0")
}

apollo {
    // Kotlinコードを生成するプラグイン
    generateKotlinModels.set(true)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
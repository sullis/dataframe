import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")

    id("org.jetbrains.kotlinx.dataframe")

    // only mandatory if `kotlin.dataframe.add.ksp=false` in gradle.properties
    id("com.google.devtools.ksp")
}

repositories {
    mavenCentral()
    mavenLocal() // in case of local dataframe development
}

application.mainClass.set("org.jetbrains.kotlinx.dataframe.examples.movies.MoviesWithDataClassKt")

dependencies {
    // implementation("org.jetbrains.kotlinx:dataframe:X.Y.Z")
    implementation(project(":"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

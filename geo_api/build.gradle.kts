plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

dependencies {

    //  Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    //  Geocode
    implementation(libs.google.geocode)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
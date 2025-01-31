plugins {
    kotlin("jvm") version "2.1.0"
}

group = "xyz.minor"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.lwjgl:lwjgl:3.3.2")
    implementation("org.lwjgl:lwjgl-opengl:3.3.2")
    implementation("org.lwjgl:lwjgl-glfw:3.3.2")
    implementation("org.lwjgl:lwjgl-stb:3.3.2")
    runtimeOnly("org.lwjgl:lwjgl:3.3.2:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-opengl:3.3.2:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-glfw:3.3.2:natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-stb:3.3.2:natives-windows")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

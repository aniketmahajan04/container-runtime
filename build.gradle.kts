plugins {
    id("java")
    application
}

group = "com.sendo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass.set("com.sendo.cli.Main")   // <-- fully qualified class name
}

tasks.test {
    useJUnitPlatform()
}

plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.3")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

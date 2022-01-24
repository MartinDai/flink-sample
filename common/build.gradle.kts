plugins {
    id("java-library-conventions")
}

dependencies {
    implementation("org.projectlombok:lombok:1.18.22")
    annotationProcessor ("org.projectlombok:lombok:1.18.22")

    api("org.apache.commons:commons-lang3:3.12.0")
    api("org.slf4j:slf4j-api:1.7.33")
    api("com.fasterxml.jackson.core:jackson-databind:2.13.1")
}
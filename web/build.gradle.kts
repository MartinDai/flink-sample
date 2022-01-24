plugins {
    id("java-library-conventions")
    id("org.springframework.boot") version "2.5.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

dependencies {
    api(project(":common"))

    implementation("org.projectlombok:lombok:1.18.22")
    annotationProcessor ("org.projectlombok:lombok:1.18.22")

    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.kafka:spring-kafka")
    api("ch.qos.logback:logback-core:1.2.10")
}


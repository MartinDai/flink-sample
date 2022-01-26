plugins {
    id("java-library-conventions")
    id("org.springframework.boot") version "2.5.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

dependencies {
    api(project(":common"))

    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.kafka:spring-kafka")
}


plugins {
    id("java-library-conventions")
    alias(libs.plugins.springboot)
    alias(libs.plugins.springDependencyManagement)
}

dependencies {
    api(project(":common"))

    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.kafka:spring-kafka")
}


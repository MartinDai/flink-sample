dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("flink","1.14.0")
            library("flink-core", "org.apache.flink", "flink-core").versionRef("flink")
            library("flink-kafka", "org.apache.flink", "flink-connector-kafka_2.12").versionRef("flink")
            library("flink-streaming", "org.apache.flink", "flink-streaming-java_2.12").versionRef("flink")
            library("flink-clients", "org.apache.flink", "flink-clients_2.12").versionRef("flink")
            bundle("flink", listOf("flink-core", "flink-kafka", "flink-streaming", "flink-clients"))
            library("slf4j","org.slf4j","slf4j-api").version("1.7.33")
            library("jackson","com.fasterxml.jackson.core","jackson-databind").version("2.13.1")
            library("commons-lang3","org.apache.commons", "commons-lang3").version {
                strictly("[3.10, 4.0[")
                prefer("3.12.0")
            }
            plugin("springboot", "org.springframework.boot").version("2.5.6")
            plugin("springDependencyManagement", "io.spring.dependency-management").version("1.0.11.RELEASE")
            plugin("shadow", "com.github.johnrengelman.shadow").version("7.1.2")
        }
    }
}

rootProject.name = "flink-sample"
include(":common")
include(":web")
include(":jobs:notice-count-job")

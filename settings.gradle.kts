enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            alias("lombok").to("org.projectlombok:lombok:1.18.22")
            alias("flink-core").to("org.apache.flink:flink-core:1.14.0")
            alias("flink-connector-kafka").to("org.apache.flink:flink-connector-kafka_2.12:1.14.0")
            alias("flink-streaming-java").to("org.apache.flink:flink-streaming-java_2.12:1.14.0")
            alias("flink-clients").to("org.apache.flink:flink-clients_2.12:1.14.0")
            alias("slf4j-api").to("org.slf4j:slf4j-api:1.7.33")
            alias("jackson-databind").to("com.fasterxml.jackson.core:jackson-databind:2.13.1")
            alias("commons-lang3").to("org.apache.commons", "commons-lang3").version {
                strictly("[3.10, 4.0[")
                prefer("3.12.0")
            }
        }
    }
}

rootProject.name = "flink-sample"
include(":common")
include(":web")
include(":jobs:notice-count-job")

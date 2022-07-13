enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("slf4j","org.slf4j","slf4j-api").version("1.7.33")
            library("jackson","com.fasterxml.jackson.core","jackson-databind").version("2.13.1")
            library("commons-lang3","org.apache.commons", "commons-lang3").version {
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

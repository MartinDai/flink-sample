plugins {
    id("java-library-conventions")
}

dependencies {
    api(libs.commons.lang3)
    api(libs.slf4j.api)
    api(libs.jackson.databind)
}
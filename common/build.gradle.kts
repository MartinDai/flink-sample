plugins {
    id("java-library-conventions")
}

dependencies {
    implementation(libs.lombok)
    annotationProcessor(libs.lombok)

    api(libs.commons.lang3)
    api(libs.slf4j.api)
    api(libs.jackson.databind)
}
plugins {
    id("java-library-conventions")
}

dependencies {
    implementation("org.apache.flink:flink-core:1.14.0")
    implementation("org.apache.flink:flink-connector-kafka_2.12:1.14.0")
    implementation("org.apache.flink:flink-streaming-java_2.12:1.14.0")
    implementation("org.apache.flink:flink-clients_2.12:1.14.0")
}


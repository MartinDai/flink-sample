import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library-conventions")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

tasks {
    named<ShadowJar>("shadowJar") {
        dependencies{
            exclude(dependency("org.apache.flink:flink-shaded.*"))
            exclude(dependency("org.apache.flink:flink-streaming.*"))
            exclude(dependency("org.apache.flink:flink-clients.*"))
            exclude(dependency("org.apache.flink:flink-scala.*"))
            exclude(dependency("org.apache.flink:flink-runtime.*"))
            exclude(dependency("org.apache.flink:flink-rpc.*"))
            exclude(dependency("org.apache.flink:flink-queryable.*"))
            exclude(dependency("org.apache.flink:flink-optimizer.*"))
            exclude(dependency("org.apache.flink:flink-metrics.*"))
            exclude(dependency("org.apache.flink:flink-java.*"))
            exclude(dependency("org.apache.flink:flink-hadoop.*"))
            exclude(dependency("org.apache.flink:flink-file.*"))
            exclude(dependency("org.apache.flink:flink-core.*"))
            exclude(dependency("org.apache.flink:flink-annotations.*"))
            exclude(dependency("com.google.code.findbugs:jsr305:.*"))
            exclude(dependency("org.projectlombok:lombok:.*"))
            exclude(dependency("org.slf4j:.*"))
            exclude(dependency("com.esotericsoftware.kryo:.*"))
            exclude(dependency("com.esotericsoftware.minlog:.*"))
            exclude(dependency("com.github.luben:.*"))
            exclude(dependency("org.xerial.snappy:.*"))
            exclude(dependency("org.lz4:.*"))
            exclude(dependency("org.scala-lang:.*"))
        }
        mergeServiceFiles()
        minimize()
        manifest {
            attributes["Main-Class"] = "com.doodl6.flink.NoticeCountJob"
        }
    }
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }
}

dependencies {
    api(project(":common"))

    implementation("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")

    api("org.apache.flink:flink-connector-kafka_2.12:1.14.0")
    implementation("org.apache.flink:flink-streaming-java_2.12:1.14.0")
    implementation("org.apache.flink:flink-clients_2.12:1.14.0")
}

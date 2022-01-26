import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library-conventions")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

// 定义需要被打包进fatJar的依赖
tasks {
    named<ShadowJar>("shadowJar") {
        dependencies {
            include(dependency("org.apache.commons:commons-lang3:.*"))
            include(dependency("org.apache.flink:flink-connector.*"))
            include(dependency("org.apache.kafka:kafka-clients:.*"))
            include(dependency("com.fasterxml.jackson.core:.*"))
            include(dependency("com.fasterxml.jackson:.*"))
        }
        mergeServiceFiles()
        minimize()
        manifest {
            attributes["Main-Class"] = "com.doodl6.flink.NoticeCountJob"
        }
    }
}

// 把shadowJar任务加入到assemble任务流程中
tasks {
    assemble {
        dependsOn(shadowJar)
    }
}

// 排除不需要的依赖
configurations {
    "implementation" {
        exclude(group = "org.apache.flink", module = "flink-shaded-force-shading")
        exclude(group = "com.github.luben", module = "zstd-jni")
        exclude(group = "org.lz4", module = "lz4-java")
        exclude(group = "org.xerial.snappy", module = "snappy-java")
        exclude(group = "com.google.code.findbugs", module = "jsr305")
        exclude(group = "commons-collections", module = "commons-collections")
        exclude(group = "org.apache.commons", module = "commons-compress")
    }
}

dependencies {
    api(project(":common"))

    implementation(libs.flink.core)
    implementation(libs.flink.connector.kafka)
    implementation(libs.flink.streaming.java)
    implementation(libs.flink.clients)
}

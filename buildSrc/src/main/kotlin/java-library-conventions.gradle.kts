plugins {
    `java-library`
}

repositories {
    //首先去本地仓库找
    mavenLocal()
    //然后去阿里仓库找
    maven("https://maven.aliyun.com/nexus/content/groups/public/")
    //最后从maven中央仓库找
    mavenCentral()
    mavenLocal()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")
}


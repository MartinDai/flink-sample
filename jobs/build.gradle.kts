subprojects {
  group = "com.doodl6.flink.jobs"
  version = "1.0-SNAPSHOT"

  val proj = this
  plugins.withId("java") {
    configure<BasePluginExtension> {
      archivesName.set("flink-${proj.name}")
    }
  }
}

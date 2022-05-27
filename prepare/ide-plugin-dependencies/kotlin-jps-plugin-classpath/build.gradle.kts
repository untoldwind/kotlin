idePluginDependency {
    @Suppress("UNCHECKED_CAST")
    val embeddedDependencies = rootProject.extra["kotlinJpsPluginEmbeddedDependencies"] as List<String>
    @Suppress("UNCHECKED_CAST")
    val mavenDependencies = rootProject.extra["kotlinJpsPluginMavenDependencies"] as List<String>

    val otherProjects = listOf(":jps:jps-plugin", ":jps:jps-common")

    publishProjectJars(embeddedDependencies + mavenDependencies + otherProjects, libraryDependencies = listOf(protobufFull()))
}

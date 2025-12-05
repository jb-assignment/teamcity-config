import dev.panuszewski.distributedkotest.teamcity.DistributedTests
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.ui.add
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

fun Project.exampleBuild() {
    val exampleBuildVcsRoot = GitVcsRoot {
        id("ExampleBuild")
        name = "Example Build"
        url = "git@github.com:jb-assignment/example-build.git"
        branch = "main"
        authMethod = uploadedKey { uploadedKey = "id_allegro_open_source" }
    }

    val exampleBuildTests = DistributedTests(
        testTask = "test",
        numberOfBatches = 5,
        debugMode = true
    ) {
        name = "Example build tests"
        id("example_build_tests")
        vcs { root(exampleBuildVcsRoot) }
        triggers { vcs { } }
        requirements { add { matches("teamcity.agent.jvm.os.family", "Linux") } }
    }

    vcsRoot(exampleBuildVcsRoot)
    buildType(exampleBuildTests)
}
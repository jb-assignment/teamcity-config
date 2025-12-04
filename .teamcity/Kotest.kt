import dev.panuszewski.distributedkotest.teamcity.DistributedTests
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.ui.add
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

fun Project.kotest() {
    val kotestVcsRoot = GitVcsRoot {
        id("Kotest")
        name = "Kotest"
        url = "git@github.com:jb-assignment/kotest.git"
        branch = "master"
        authMethod = uploadedKey { uploadedKey = "id_allegro_open_source" }
    }

    val kotestTests = DistributedTests(
        testTask = "jvmTest",
        numberOfBatches = 5,
        debugMode = true
    ) {
        name = "Kotest tests"
        id("kotest_tests")
        vcs { root(kotestVcsRoot) }
        triggers { vcs { } }
        requirements { add { matches("teamcity.agent.jvm.os.family", "Linux") } }
    }

    vcsRoot(kotestVcsRoot)
    buildType(kotestTests)
}
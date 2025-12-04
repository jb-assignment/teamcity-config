import dev.panuszewski.distributedkotest.teamcity.DistributedTests
import jetbrains.buildServer.configs.kotlin.DslContext
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.ui.add
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

fun Project.dokka() {
    val dokkaVcsRoot = GitVcsRoot {
        id("Dokka")
        name = "Dokka"
        url = "git@github.com:jb-assignment/dokka.git"
        branch = "master"
        authMethod = uploadedKey { uploadedKey = "id_allegro_open_source" }
    }

    val dokkaTests = DistributedTests(
        testTask = "check",
        numberOfBatches = 1
    ) {
        name = "Dokka tests"
        id("dokka_tests")
        vcs { root(DslContext.settingsRoot) }
        triggers { vcs { } }
        requirements { add { matches("teamcity.agent.jvm.os.family", "Linux") } }
    }

    vcsRoot(dokkaVcsRoot)
    buildType(dokkaTests)
}
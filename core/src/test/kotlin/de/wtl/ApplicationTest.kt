package de.wtl

import de.wtl.core.module
import io.kotest.matchers.shouldBe
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.config.MapApplicationConfig
import io.ktor.server.testing.testApplication
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.lifecycle.Startables
import org.testcontainers.utility.DockerImageName
import kotlin.test.Test

class ApplicationTest {
    companion object {
        @JvmStatic
        private val mongoDb = MongoDBContainer(DockerImageName.parse("mongo:7.0.5"))

        init {
            Startables.deepStart(mongoDb).join()
        }
    }

    @Test
    fun testRoot() = testApplication {
        environment {
            config = MapApplicationConfig("database.url" to mongoDb.replicaSetUrl, "database.db" to "wtl")
        }
        application {
            module()
        }
        client.get("/metrics").apply {
            status shouldBe HttpStatusCode.OK
        }
    }
}

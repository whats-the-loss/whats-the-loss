package de.wtl

import de.wtl.core.module
import io.ktor.server.config.MapApplicationConfig
import io.ktor.server.config.mergeWith
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import org.koin.core.module.Module
import org.koin.ksp.generated.defaultModule
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.lifecycle.Startables
import org.testcontainers.utility.DockerImageName

abstract class IntegrationTest {
    companion object {
        @JvmStatic
        private val mongoDb = MongoDBContainer(DockerImageName.parse("mongo:7.0.5"))

        init {
            Startables.deepStart(mongoDb).join()
        }
    }

    protected fun test(
        module: Module = defaultModule,
        vararg additionalApplicationConfig: Pair<String, String>,
        test: suspend ApplicationTestBuilder.() -> Unit,
    ) = testApplication {
        environment {
            config = MapApplicationConfig("database.url" to mongoDb.replicaSetUrl, "database.db" to "wtl")
                .mergeWith(MapApplicationConfig(*additionalApplicationConfig))
        }
        application {
            module(module)
        }

        test()
    }
}

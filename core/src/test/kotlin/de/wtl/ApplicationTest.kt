package de.wtl

import de.wtl.core.module
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.config.MapApplicationConfig
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        environment {
            config = MapApplicationConfig()
        }
        application {
            module()
        }
        client.get("/metrics").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }
}

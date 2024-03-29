package de.wtl

import io.kotest.matchers.shouldBe
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlin.test.Test

class ApplicationTest : IntegrationTest() {
    @Test
    fun `test metrics endpoint`() = test {
        client.get("/metrics").status shouldBe HttpStatusCode.OK
    }
}

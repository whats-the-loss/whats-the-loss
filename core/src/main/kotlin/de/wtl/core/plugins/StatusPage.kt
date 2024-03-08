package de.wtl.core.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun Application.configureStatusPage() {
    install(StatusPages) {
        exception<RequestValidationException> { call, cause ->
            logger.debug(cause) { "client validation error" }
            call.respond(HttpStatusCode.UnprocessableEntity, cause.reasons)
        }
        exception<Throwable> { call, cause ->
            logger.error(cause) { "Internal server error" }
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}

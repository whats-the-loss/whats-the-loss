package de.wtl.core.plugins

import de.wtl.api.model.ErrorResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.ContentTransformationException
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.plugins.UnsupportedMediaTypeException
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun Application.configureStatusPage() {
    install(StatusPages) {
        exception<RequestValidationException> { call, exception ->
            logger.debug(exception) { "Request Validation Exception" }
            call.respond(HttpStatusCode.UnprocessableEntity, ErrorResponse(exception.reasons))
        }
        exception<BadRequestException> { call, exception ->
            logger.debug(exception) { "Bad Request" }
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(listOf(exception.cause?.message ?: "Bad Request")))
        }
        exception<NotFoundException> { call, exception ->
            logger.debug(exception) { "Not Found" }
            call.respond(HttpStatusCode.NotFound, ErrorResponse(listOf(exception.message ?: "Not Found")))
        }
        exception<ContentTransformationException> { call, exception ->
            logger.debug(exception) { "Content Transformation Exception" }
            val messages = listOf(exception.message ?: "Content Transformation Exception")
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(messages))
        }
        exception<UnsupportedMediaTypeException> { call, exception ->
            logger.debug(exception) { "Unsupported Media Type" }
            val messages = listOf(exception.message ?: "Unsupported Media Type")
            call.respond(HttpStatusCode.UnsupportedMediaType, ErrorResponse(messages))
        }
        exception<Throwable> { call, cause ->
            logger.error(cause) { "Internal server error" }
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}

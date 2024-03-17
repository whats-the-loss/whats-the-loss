package de.wtl.core.plugins

import de.wtl.core.utils.RouterFactory
import de.wtl.core.utils.ValidatorFactory
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.routing.routing
import org.koin.ktor.ext.getKoin

fun Application.configureRouting() {
    install(AutoHeadResponse)
    install(RequestValidation) {
        getKoin().getAll<ValidatorFactory<*>>().forEach { factory -> factory.register(this) }
    }
    routing {
        getKoin().getAll<RouterFactory>().forEach { factory -> factory.register(this) }
        openAPI(path = "openapi", swaggerFile = "openapi.yaml")
    }
}

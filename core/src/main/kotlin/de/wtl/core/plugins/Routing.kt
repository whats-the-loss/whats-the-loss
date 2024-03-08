package de.wtl.core.plugins

import de.wtl.core.run.RunValidator
import de.wtl.core.run.registerRunRoutes
import de.wtl.core.utils.register
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    install(AutoHeadResponse)
    install(RequestValidation) {
        register(RunValidator)
    }
    routing {
        registerRunRoutes()
    }
}

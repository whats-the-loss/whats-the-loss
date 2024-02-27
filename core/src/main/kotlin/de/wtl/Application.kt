package de.wtl

import de.wtl.plugins.configureHTTP
import de.wtl.plugins.configureMonitoring
import de.wtl.plugins.configureRouting
import de.wtl.plugins.configureSecurity
import de.wtl.plugins.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureSecurity()
    configureRouting()
}

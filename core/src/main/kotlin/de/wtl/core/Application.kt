package de.wtl.core

import de.wtl.core.plugins.configureHTTP
import de.wtl.core.plugins.configureKoin
import de.wtl.core.plugins.configureMonitoring
import de.wtl.core.plugins.configureRouting
import de.wtl.core.plugins.configureSecurity
import de.wtl.core.plugins.configureSerialization
import de.wtl.core.plugins.configureWebSockets
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    configureHTTP()
    configureKoin()
    configureMonitoring()
    configureRouting()
    configureSecurity()
    configureSerialization()
    configureWebSockets()
}

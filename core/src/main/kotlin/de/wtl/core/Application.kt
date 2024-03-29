package de.wtl.core

import de.wtl.core.plugins.configureHTTP
import de.wtl.core.plugins.configureKoin
import de.wtl.core.plugins.configureMonitoring
import de.wtl.core.plugins.configureRouting
import de.wtl.core.plugins.configureSecurity
import de.wtl.core.plugins.configureSerialization
import de.wtl.core.plugins.configureStatusPage
import de.wtl.core.plugins.configureWebSockets
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    configureHTTP()
    configureKoin()
    configureMonitoring()
    configureRouting()
    configureSecurity()
    configureSerialization()
    configureStatusPage()
    configureWebSockets()
}

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
import org.koin.core.module.Module
import org.koin.ksp.generated.defaultModule

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module(module: Module = defaultModule) {
    configureHTTP()
    configureKoin(module)
    configureMonitoring()
    configureRouting()
    configureSecurity()
    configureSerialization()
    configureStatusPage()
    configureWebSockets()
}

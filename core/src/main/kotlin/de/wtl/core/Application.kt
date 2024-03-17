package de.wtl.core

import de.wtl.core.plugins.configureHTTP
import de.wtl.core.plugins.configureKoin
import de.wtl.core.plugins.configureMonitoring
import de.wtl.core.plugins.configureRouting
import de.wtl.core.plugins.configureSecurity
import de.wtl.core.plugins.configureSerialization
import de.wtl.core.plugins.configureStatusPage
import de.wtl.core.plugins.configureWebSockets
import io.jstach.rainbowgum.LogOutput
import io.jstach.rainbowgum.RainbowGum
import io.jstach.rainbowgum.pattern.format.PatternEncoderBuilder
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.lang.System.Logger.Level

const val LOG_PATTERN = "%cyan(%d{YYYY-MM-dd HH:mm:ss.SSS}) [%yellow(%thread)] %highlight(%-5level) %logger{36}: %msg%n"

fun main() {
    RainbowGum.builder()
        .route { builder ->
            builder.level(Level.INFO)
            builder.appender("console") { appender ->
                val encoder = PatternEncoderBuilder("console").pattern(LOG_PATTERN).build()

                appender.encoder(encoder)
                appender.output(LogOutput.ofStandardOut())
            }
        }
        .set()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

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

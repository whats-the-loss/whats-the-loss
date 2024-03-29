package de.wtl.core.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin(module: Module) {
    install(Koin) {
        slf4jLogger()
        modules(module, module { single { environment.config } })
    }
}

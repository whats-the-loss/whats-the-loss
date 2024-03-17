package de.wtl.core.config

import io.ktor.server.config.ApplicationConfig
import org.koin.core.annotation.Factory

data class DatabaseConfig(
    val host: String,
    val user: String,
    val password: String,
    val port: UInt,
    val database: String,
)

@Factory
fun databaseConfig(config: ApplicationConfig): DatabaseConfig = DatabaseConfig(
    config.propertyOrNull("database.host")?.getString() ?: "localhost",
    config.propertyOrNull("database.user")?.getString() ?: "rootuser",
    config.propertyOrNull("database.password")?.getString() ?: "rootpass",
    config.propertyOrNull("database.port")?.getString()?.toUInt() ?: 27017u,
    config.propertyOrNull("database.db")?.getString() ?: "wtl",
)

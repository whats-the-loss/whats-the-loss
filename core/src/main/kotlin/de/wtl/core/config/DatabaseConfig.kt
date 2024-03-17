package de.wtl.core.config

import io.ktor.server.config.ApplicationConfig
import org.koin.core.annotation.Factory

data class DatabaseConfig(val url: String, val database: String)

@Factory
fun databaseConfig(config: ApplicationConfig): DatabaseConfig = DatabaseConfig(
    config.propertyOrNull("database.url")?.getString() ?: "mongodb://rootuser:rootpass@localhost:27017",
    config.propertyOrNull("database.db")?.getString() ?: "wtl",
)

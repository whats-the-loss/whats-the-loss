package de.wtl.core.plugins

import com.mongodb.kotlin.client.coroutine.MongoClient
import io.ktor.http.ContentType
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.response.respond
import io.ktor.server.response.respondOutputStream
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.util.cio.ChannelWriteException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

const val connectionString = "mongodb://rootuser:rootpass@localhost:27017"
val client = MongoClient.create(connectionString = connectionString)

fun Application.configureRouting() {
    install(AutoHeadResponse)
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/mongo") {
            call.respondOutputStream(contentType = ContentType.Text.Plain) {
                val writer = bufferedWriter()
                client.listDatabaseNames().collect {
                    println(it)
                    try {
                        withContext(Dispatchers.IO) {
                            writer.write(it)
                            writer.newLine()
                            writer.flush()
                        }
                        delay(1000)
                    } catch (_: ChannelWriteException) {
                        cancel()
                    }
                }
            }
        }
        get("/mongo2") {
            call.respond(client.listDatabaseNames().onEach { delay(1000) })
        }
    }
}

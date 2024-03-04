package de.wtl.core.plugins

import com.mongodb.kotlin.client.coroutine.MongoClient
import de.wtl.api.model.RunLogSchema
import de.wtl.api.model.RunProperties
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.OffsetDateTime

private const val CONNECTION_STRING = "mongodb://rootuser:rootpass@localhost:27017"
private val client = MongoClient.create(connectionString = CONNECTION_STRING)

fun Application.configureRouting() {
    install(AutoHeadResponse)
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        webSocket("/mongo") {
            client.listDatabaseNames().collect {
                delay(1000)
                send(Frame.Text(it))
            }
        }

        webSocket("/echo") {
            send(Frame.Text("Please enter your name"))
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val receivedText = frame.readText()
                if (receivedText.equals("bye", ignoreCase = true)) {
                    close(CloseReason(CloseReason.Codes.NORMAL, "Client said BYE"))
                } else {
                    send(Frame.Text("Hi, $receivedText!"))
                }
            }
        }

        webSocket("/echo/{name}") {
            println("Hello from ${call.parameters["name"]}")
            send(Frame.Text("Hi, ${call.parameters["name"]}!"))
        }
    }
}

sealed interface Event {
    // Hey Frontend, here is a new point in time
    sealed interface PointEvent<T> : Event {
        val time: OffsetDateTime
        val value: T

        data class Container(
            override val time: OffsetDateTime,
            override val value: List<Number>,
        ) : PointEvent<List<Number>>

        data class Scalar(override val time: OffsetDateTime, override val value: Number) : PointEvent<Number>
    }

    // Hey Frontend, here is a new run or the schema for the run changed
    data class Run(val run: RunProperties, val schema: RunLogSchema) : Event
}

// TODO jg:
// - Test websocket with url parameters ✅
// - API
// - Database change sets

// TODO jj:
// -

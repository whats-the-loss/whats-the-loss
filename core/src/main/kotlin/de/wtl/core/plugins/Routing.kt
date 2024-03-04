package de.wtl.core.plugins

import com.mongodb.kotlin.client.coroutine.MongoClient
import de.wtl.api.model.RunLogSchema
import de.wtl.api.model.RunProperties
import de.wtl.core.run.Run
import de.wtl.core.run.RunValidator
import de.wtl.core.run.registerRunRoutes
import de.wtl.core.utils.register
import io.ktor.resources.Resource
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.resources.Resources
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.Frame
import java.time.Duration
import java.time.OffsetDateTime

private const val CONNECTION_STRING = "mongodb://rootuser:rootpass@localhost:27017"
private val client = MongoClient.create(connectionString = CONNECTION_STRING)
private val database = client.getDatabase("wtl")

fun Application.configureRouting() {
    install(AutoHeadResponse)
    install(Resources)
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    install(RequestValidation) {
        register(RunValidator)
    }

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        registerRunRoutes()

        webSocket("/runs") {
            database.getCollection<Run>("run").watch().collect {
                send(Frame.Text(it.fullDocument.toString()))
            }
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

@Resource("/articles")
class RunResource(val sort: String? = "new") {
    @Resource("new")
    class New(val parent: RunResource = RunResource())

    @Resource("{id}")
    class Id(val parent: RunResource = RunResource(), val id: Long)
}

// TODO jg:
// - Test websocket with url parameters ✅
// - API
// - Database change sets

// TODO jj:
// -

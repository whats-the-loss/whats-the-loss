package de.wtl.core.plugins

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import de.wtl.api.model.RunLogSchema
import de.wtl.api.model.RunProperties
import de.wtl.core.run.RunValidator
import de.wtl.core.run.registerRunRoutes
import de.wtl.core.utils.register
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.Frame
import org.koin.ktor.ext.get
import java.time.OffsetDateTime

fun Application.configureRouting() {
    install(AutoHeadResponse)
    install(RequestValidation) {
        register(RunValidator)
    }
    routing {
        val database: MongoDatabase = get()

        get("/") {
            call.respondText("Hello World!")
        }

        registerRunRoutes()

        webSocket("/runs") {
            database.listCollectionNames().collect {
                send(Frame.Text(it))
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

// TODO jg:
// - Test websocket with url parameters ✅
// - API
// - Database change sets

// TODO jj:
// -

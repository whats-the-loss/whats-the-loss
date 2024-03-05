package de.wtl.core.run

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.coroutines.flow.toList
import org.koin.ktor.ext.get

fun Routing.registerRunRoutes() {
    val database: MongoDatabase = get()

    get("/runs") {
        val runs = database.getCollection<Run>("run").find().toList()
        call.respond(runs)
    }
}

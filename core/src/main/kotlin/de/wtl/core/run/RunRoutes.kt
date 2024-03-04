package de.wtl.core.run

import de.wtl.core.plugins.database
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.coroutines.flow.toList

fun Routing.registerRunRoutes() {
    get("/runs") {
        val runs = database.getCollection<Run>("run").find().toList()
        call.respond(runs)
    }
}

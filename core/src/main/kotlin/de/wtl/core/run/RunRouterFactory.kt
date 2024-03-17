package de.wtl.core.run

import de.wtl.api.model.RunCreateRequest
import de.wtl.api.model.RunUpdateRequest
import de.wtl.core.persistence.OperationResult
import de.wtl.core.utils.Router
import de.wtl.core.utils.RouterFactory
import de.wtl.core.utils.toObjectIdOrNull
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class RunRouterFactory(private val repository: RunRepository) : RouterFactory {
    override fun router() = Router {
        post("/runs") {
            val request = call.receive<RunCreateRequest>()
            val run = request.toRun()
            repository.create(run)
            call.respond(HttpStatusCode.Created, run.toCreateDtoResponse())
        }
        get("/runs") {
            val runs = repository.findAll().map(Run::toDtoResponse)
            call.respond(runs)
        }
        put("/runs/{id}") {
            val id = call.parameters["id"]?.toObjectIdOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
            val run = call.receive<RunUpdateRequest>()

            when (repository.update(id, run.configuration, run.status)) {
                OperationResult.NotFound -> call.respond(HttpStatusCode.NotFound)
                OperationResult.Success -> call.respond(HttpStatusCode.NoContent)
            }
        }
        delete("/runs/{id}") {
            val id = call.parameters["id"]?.toObjectIdOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            repository.delete(id)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}

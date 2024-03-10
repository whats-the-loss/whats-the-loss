package de.wtl.core.run

import de.wtl.api.model.RunCreateRequest
import de.wtl.api.model.RunCreateResponse
import de.wtl.api.model.RunGetResponse
import de.wtl.api.model.RunStatus
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.bson.BsonDocument
import org.bson.types.ObjectId

fun RunCreateRequest.toRun(id: ObjectId = ObjectId()) = Run(
    id,
    project,
    experiment,
    name,
    groups,
    RunStatus.RUNNING,
    configuration.toBsonDocument(),
)

fun JsonObject.toBsonDocument(): BsonDocument = BsonDocument.parse(Json.encodeToString(JsonObject.serializer(), this))

fun Run.toDtoResponse() = RunGetResponse(
    project,
    experiment,
    groups,
    name,
    configuration.toJsonObject(),
    id.toHexString(),
    status,
)

fun BsonDocument.toJsonObject() = Json.decodeFromString(JsonObject.serializer(), toJson())

fun Run.toCreateDtoResponse() = RunCreateResponse(id.toHexString())

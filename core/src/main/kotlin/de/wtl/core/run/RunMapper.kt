package de.wtl.core.run

import de.wtl.api.model.RunCreateRequest
import de.wtl.api.model.RunCreateResponse
import de.wtl.api.model.RunGetResponse
import de.wtl.api.model.RunStatus
import org.bson.types.ObjectId

fun RunCreateRequest.toRun(id: ObjectId = ObjectId()) = Run(
    id,
    project,
    experiment,
    name,
    groups,
    RunStatus.RUNNING,
    configuration,
)

fun Run.toDtoResponse() = RunGetResponse(
    project,
    experiment,
    groups,
    name,
    configuration,
    id.toHexString(),
    status,
)

fun Run.toCreateDtoResponse() = RunCreateResponse(id.toHexString())

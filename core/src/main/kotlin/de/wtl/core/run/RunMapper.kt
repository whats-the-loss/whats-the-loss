package de.wtl.core.run

import org.bson.types.ObjectId

fun RunDtoRequest.toRun(id: ObjectId = ObjectId()) = Run(id, project, experiment, name, group)

fun Run.toDtoResponse() = RunDtoResponse(id.toHexString(), project, experiment, name, group)

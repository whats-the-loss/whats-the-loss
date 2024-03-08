package de.wtl.core.run

import kotlinx.serialization.Serializable

@Serializable
data class RunDtoResponse(
    val id: String,
    val project: String,
    val experiment: String,
    val name: String,
    val group: Set<String>,
)

@Serializable
data class RunDtoRequest(
    val project: String,
    val experiment: String,
    val name: String,
    val group: Set<String>,
)

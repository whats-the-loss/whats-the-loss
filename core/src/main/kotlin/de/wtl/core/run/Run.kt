package de.wtl.core.run

import de.wtl.core.persistence.Identifiable
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Run(
    @SerialName("_id") @Contextual override val id: ObjectId,
    val project: String,
    val experiment: String,
    val name: String,
    val group: Set<String>,
) : Identifiable

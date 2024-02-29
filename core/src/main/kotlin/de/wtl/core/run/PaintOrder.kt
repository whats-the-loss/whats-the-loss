package de.wtl.core.run

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class PaintOrder(
    @SerialName("_id") @Contextual val id: ObjectId?,
    val color: String,
    val qty: Int,
    @SerialName("brand") val manufacturer: String = "Acme",
)

package de.wtl.core.persistence

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.bson.BsonDocument

fun JsonObject.toBsonDocument(): BsonDocument = BsonDocument.parse(Json.encodeToString(JsonObject.serializer(), this))

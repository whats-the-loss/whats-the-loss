package de.wtl.core.persistence

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.bson.codecs.kotlinx.BsonValueSerializer

@OptIn(ExperimentalSerializationApi::class)
object JsonObjectSerializer : KSerializer<JsonObject> {
    override val descriptor: SerialDescriptor = BsonValueSerializer.descriptor

    override fun deserialize(decoder: Decoder): JsonObject {
        val result = BsonValueSerializer.deserialize(decoder)
        return Json.decodeFromString(JsonObject.serializer(), result.asDocument().toJson())
    }

    override fun serialize(encoder: Encoder, value: JsonObject) {
        val converted = value.toBsonDocument()
        BsonValueSerializer.serialize(encoder, converted)
    }
}

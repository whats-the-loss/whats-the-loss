package de.wtl.core.persistence

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.ChangeStreamFlow
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.koin.core.annotation.Single

interface Identifiable {
    val id: ObjectId
}

@Single
fun database() = MongoClient.create(connectionString = "mongodb://rootuser:rootpass@localhost:27017").getDatabase("wtl")

interface MongoDbRepository<T : Identifiable> {
    suspend fun create(identifiable: T)
    suspend fun findOrNull(id: ObjectId): T?
    suspend fun update(identifiable: T): OperationResult
    suspend fun delete(id: ObjectId): OperationResult
    fun findAll(): Flow<T>
    fun watch(pipeline: List<Bson> = emptyList()): ChangeStreamFlow<T>
}

sealed interface OperationResult {
    data object NotFound : OperationResult
    data object Success : OperationResult
}

@Suppress("MemberVisibilityCanBePrivate")
open class SimpleMongoDbRepository<T : Identifiable>(
    protected val database: MongoDatabase,
    protected val collectionName: String,
    protected val type: Class<T>,
) : MongoDbRepository<T> {

    override suspend fun create(identifiable: T) {
        database.getCollection(collectionName, type).insertOne(identifiable)
    }

    override suspend fun findOrNull(id: ObjectId): T? = database.getCollection(collectionName, type)
        .find(Filters.eq(id), type)
        .firstOrNull()

    override fun findAll(): Flow<T> = database.getCollection(collectionName, type).find()

    override suspend fun update(identifiable: T): OperationResult {
        val result = database.getCollection(collectionName, type).replaceOne(Filters.eq(identifiable.id), identifiable)

        return if (result.wasAcknowledged() && result.matchedCount > 0) {
            OperationResult.Success
        } else {
            OperationResult.NotFound
        }
    }

    override suspend fun delete(id: ObjectId): OperationResult {
        val result = database.getCollection(collectionName, type).deleteOne(Filters.eq(id))
        return if (result.wasAcknowledged() && result.deletedCount > 0) {
            OperationResult.Success
        } else {
            OperationResult.NotFound
        }
    }

    override fun watch(pipeline: List<Bson>) = database.getCollection(collectionName, type).watch(pipeline)
}

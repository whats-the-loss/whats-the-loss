package de.wtl.core.run

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import de.wtl.api.model.RunStatus
import de.wtl.core.persistence.MongoDbRepository
import de.wtl.core.persistence.OperationResult
import de.wtl.core.persistence.SimpleMongoDbRepository
import org.bson.BsonDocument
import org.bson.types.ObjectId
import org.koin.core.annotation.Singleton

interface RunRepository : MongoDbRepository<Run> {
    suspend fun update(id: ObjectId, configuration: BsonDocument, runStatus: RunStatus): OperationResult
}

@Singleton
class RunRepositoryImpl(database: MongoDatabase) : RunRepository,
    SimpleMongoDbRepository<Run>(database, "runs", Run::class.java) {

    override suspend fun update(id: ObjectId, configuration: BsonDocument, runStatus: RunStatus): OperationResult {
        val update = Updates.combine(
            Updates.set(Run::configuration.name, configuration),
            Updates.set(Run::status.name, runStatus),
        )
        val result = database.getCollection(collectionName, type).updateOne(Filters.eq(id), update)

        return if (result.wasAcknowledged() && result.matchedCount > 0) {
            OperationResult.Success
        } else {
            OperationResult.NotFound
        }
    }
}

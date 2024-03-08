package de.wtl.core.run

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import de.wtl.core.persistence.MongoDbRepository
import de.wtl.core.persistence.SimpleMongoDbRepository
import org.koin.core.annotation.Singleton

interface RunRepository : MongoDbRepository<Run>

@Singleton
class RunRepositoryImpl(database: MongoDatabase) : RunRepository,
    SimpleMongoDbRepository<Run>(database, "runs", Run::class.java)

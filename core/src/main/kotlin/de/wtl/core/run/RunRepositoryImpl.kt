package de.wtl.core.run

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single
import org.koin.core.annotation.Singleton

@Single
fun database() = MongoClient.create(connectionString = "mongodb://rootuser:rootpass@localhost:27017").getDatabase("wtl")

@Singleton
class RunRepositoryImpl(private val database: MongoDatabase) : RunRepository {
    override suspend fun create(run: Run) {
        TODO("Not yet implemented")
    }

    override suspend fun find(id: String): Run? {
        TODO("Not yet implemented")
    }

    override suspend fun update(run: Run) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override fun findAll(): Flow<Run> {
        TODO("Not yet implemented")
    }
}

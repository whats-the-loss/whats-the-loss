package de.wtl.core.run

import kotlinx.coroutines.flow.Flow

interface RunRepository {
    suspend fun create(run: Run)
    suspend fun find(id: String): Run?
    suspend fun update(run: Run)
    suspend fun delete(id: String)
    fun findAll(): Flow<Run>
}

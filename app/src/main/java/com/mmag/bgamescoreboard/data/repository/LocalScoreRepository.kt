package com.mmag.bgamescoreboard.data.repository

import com.mmag.bgamescoreboard.data.db.model.ScoringCategory
import kotlinx.coroutines.flow.Flow

interface LocalScoreRepository {
    suspend fun addCategory(gameId: Int, categoryName: String)
    fun getCategoriesByGameId(gameId: Int): Flow<List<ScoringCategory>>

    suspend fun addScore(playerId: Int, gameRecordId: Int, categoryId: Int, scoreAmount: Int)

    suspend fun addRecord(date: String, boardGameId: Int)
}
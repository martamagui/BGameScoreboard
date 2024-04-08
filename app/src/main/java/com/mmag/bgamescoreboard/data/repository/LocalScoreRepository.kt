package com.mmag.bgamescoreboard.data.repository

import com.mmag.bgamescoreboard.data.db.model.Score
import com.mmag.bgamescoreboard.data.db.model.ScoringCategory
import com.mmag.bgamescoreboard.data.db.model.relations.RecordWithCategories
import com.mmag.bgamescoreboard.data.db.model.relations.ScoreWithPlayer
import kotlinx.coroutines.flow.Flow

interface LocalScoreRepository {

    suspend fun addCategory(gameId: Int, categoryName: String)

    suspend fun addScore(playerId: Int, gameRecordId: Int, categoryId: Int, scoreAmount: Int)

    suspend fun addRecord(date: String, boardGameId: Int): Long

    fun getCategoriesByGameId(gameId: Int): Flow<List<ScoringCategory>>

    fun getRecordWithCategories(gameId: Int): Flow<RecordWithCategories>

    fun getCategoriesByGameRecord(gameRecordId: Int): Flow<List<ScoringCategory>>

    suspend fun getScoreByRecordIdAndPlayer(
        recordId: Int,
        playerId: Int,
        categoryId: Int
    ): Score?

    fun getScoresWithPlayersByCategory(recordId: Int, categoryId: Int): Flow<List<ScoreWithPlayer>>

}
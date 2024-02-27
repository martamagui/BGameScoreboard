package com.mmag.bgamescoreboard.data.repository

import com.mmag.bgamescoreboard.data.db.BGSDatabase
import com.mmag.bgamescoreboard.data.db.model.GameScoreRecord
import com.mmag.bgamescoreboard.data.db.model.Score
import com.mmag.bgamescoreboard.data.db.model.ScoringCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalScoreRepositoryRoomImpl @Inject constructor(
    val database: BGSDatabase
) : LocalScoreRepository {
    override suspend fun addCategory(gameId: Int, categoryName: String) {
        val category = ScoringCategory(0, gameId, categoryName)
        database.scoreDao().addCategory(category)
    }

    override fun getCategoriesByGameId(gameId: Int): Flow<List<ScoringCategory>> = database.scoreDao().getCategoriesByGameId(gameId)

    override suspend fun addScore(
        playerId: Int,
        gameRecordId: Int,
        categoryId: Int,
        scoreAmount: Int
    ) {
        val score = Score(0, playerId, gameRecordId, categoryId, scoreAmount)
        database.scoreDao().addScore(score)
    }

    override suspend fun addRecord(date: String, boardGameId: Int) {
        val record = GameScoreRecord(0, date, boardGameId)
        database.scoreDao().addRecord(record)
    }

}
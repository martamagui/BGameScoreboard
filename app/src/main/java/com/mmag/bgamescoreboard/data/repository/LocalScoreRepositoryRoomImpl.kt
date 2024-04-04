package com.mmag.bgamescoreboard.data.repository

import com.mmag.bgamescoreboard.data.db.BGSDatabase
import com.mmag.bgamescoreboard.data.db.model.GameScoreRecord
import com.mmag.bgamescoreboard.data.db.model.Score
import com.mmag.bgamescoreboard.data.db.model.ScoringCategory
import com.mmag.bgamescoreboard.data.db.model.relations.ScoreWithPlayer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalScoreRepositoryRoomImpl @Inject constructor(
    val database: BGSDatabase
) : LocalScoreRepository {
    override suspend fun addCategory(gameId: Int, categoryName: String) {
        val category = ScoringCategory(0, gameId, categoryName)
        database.scoreDao().addCategory(category)
    }

    override fun getCategoriesByGameId(gameId: Int): Flow<List<ScoringCategory>> =
        database.scoreDao().getCategoriesByGameId(gameId)

    override fun getCategoriesByGameRecord(gameRecordId: Int): Flow<List<ScoringCategory>>  = database.scoreDao().getCategoriesByGameId(gameRecordId)

    override fun getScoresWithPlayers(recordId: Int, categoryId: Int): Flow<List<ScoreWithPlayer>> =
        database.scoreDao().getScoresWithPlayers(recordId, categoryId)

    override suspend fun addScore(
        playerId: Int,
        gameRecordId: Int,
        categoryId: Int,
        scoreAmount: Int
    ) {
        val score = Score(0, playerId, gameRecordId, categoryId, scoreAmount)
        database.scoreDao().addScore(score)
    }

    override suspend fun addRecord(date: String, boardGameId: Int): Long {
        val record = GameScoreRecord(0, date, boardGameId)
        return database.scoreDao().addRecord(record)
    }

}
package com.mmag.bgamescoreboard.data.repository

import android.util.Log
import com.mmag.bgamescoreboard.data.db.BGSDatabase
import com.mmag.bgamescoreboard.data.db.model.GameScoreRecord
import com.mmag.bgamescoreboard.data.db.model.Score
import com.mmag.bgamescoreboard.data.db.model.ScoringCategory
import com.mmag.bgamescoreboard.data.db.model.relations.RecordWithCategories
import com.mmag.bgamescoreboard.data.db.model.relations.ScoreWithPlayer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ScoringRepositoryRoomImpl @Inject constructor(
    val database: BGSDatabase,
) : ScoringRepository {

    override suspend fun addCategory(gameId: Int, categoryName: String) {
        val category = ScoringCategory(0, gameId, categoryName)
        database.categoryDao().addCategory(category)
    }

    override fun getCategoriesByGameId(gameId: Int): Flow<List<ScoringCategory>> =
        database.categoryDao().getCategoriesByGameId(gameId)

    override fun getRecordsCount(): Flow<List<GameScoreRecord>> = database.recordDao().getRecords()

    override fun getRecordWithCategories(recordId: Int): Flow<RecordWithCategories?> =
        database.recordDao().getRecordWithCategories(recordId)

    override fun getCategoriesByGameRecord(gameRecordId: Int): Flow<List<ScoringCategory>> =
        database.categoryDao().getCategoriesByGameId(gameRecordId)

    override suspend fun getScoreByRecordIdAndPlayer(
        recordId: Int,
        playerId: Int,
        categoryId: Int,
    ): Score? =
        database.scoreDao().getScoreByRecordIdCategoryAndPlayer(recordId, playerId, categoryId)


    override fun getScoresWithPlayersByCategory(
        recordId: Int,
        categoryId: Int,
    ): Flow<List<ScoreWithPlayer>> =
        database.scoreDao().getScoresWithPlayers(recordId, categoryId)

    override suspend fun getPlayersScoresFromRecord(
        recordId: Int,
        playerId: Int,
    ): Int {
        val response = database.scoreDao().getPlayersScoresFromRecord(recordId, playerId).first()
        var points = 0
        response.forEach { item ->
            points += item.scoreAmount
        }
        return points
    }

    override suspend fun deleteRecordAndScores(recordId: Int) {
        val deletedRecords = database.recordDao().deleteRecordById(recordId)
        val deletedScores = database.scoreDao().deleteScoresByRecords(recordId)
        logData(
            "deletedRecords: ${deletedRecords}\n" +
                    "deletedScores: ${deletedScores}"
        )
    }

    override suspend fun addScore(
        playerId: Int,
        gameRecordId: Int,
        categoryId: Int,
        scoreAmount: Int,
    ) {
        val score = Score(0, playerId, gameRecordId, categoryId, scoreAmount)
        database.scoreDao().addScore(score)
    }

    override suspend fun updateScore(score: Score) {
        database.scoreDao().updateScore(score)
    }

    override suspend fun addRecord(date: String, boardGameId: Int): Long {
        val record = GameScoreRecord(0, date, boardGameId)
        return database.recordDao().addRecord(record)
    }


    /*region --- OTHERS ---*/
    private fun logData(msg: String) {
        Log.d("ScoreRep", msg)
    }
    /*endregion --- OTHERS ---*/


}
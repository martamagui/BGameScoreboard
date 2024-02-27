package com.mmag.bgamescoreboard.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mmag.bgamescoreboard.data.db.model.GameScoreRecord
import com.mmag.bgamescoreboard.data.db.model.Score
import com.mmag.bgamescoreboard.data.db.model.ScoringCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {
    @Insert
    suspend fun addCategory(scoringCategory: ScoringCategory)

    @Query("SELECT * FROM ScoringCategory WHERE game_id=:gameId")
    fun getCategoriesByGameId(gameId: Int): Flow<List<ScoringCategory>>

    @Insert
    suspend fun addScore(score: Score)

    @Insert
    suspend fun addRecord(gameScoreRecord: GameScoreRecord)
}
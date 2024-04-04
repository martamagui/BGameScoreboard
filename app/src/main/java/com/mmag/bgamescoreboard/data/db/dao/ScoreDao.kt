package com.mmag.bgamescoreboard.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.mmag.bgamescoreboard.data.db.model.GameScoreRecord
import com.mmag.bgamescoreboard.data.db.model.Score
import com.mmag.bgamescoreboard.data.db.model.ScoringCategory
import com.mmag.bgamescoreboard.data.db.model.relations.ScoreWithPlayer
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {
    @Insert
    suspend fun addCategory(scoringCategory: ScoringCategory)

    @Query("SELECT * FROM ScoringCategory WHERE game_id=:gameId")
    fun getCategoriesByGameId(gameId: Int): Flow<List<ScoringCategory>>


    /*@Query("SELECT * FROM GameScoreRecord WHERE board_game_id=:gameId")
    fun getCategoriesByGameReport(gameId: Int): Flow<List<ScoringCategory>>*/

    @Insert
    suspend fun addScore(score: Score)

    @Insert
    suspend fun addRecord(gameScoreRecord: GameScoreRecord): Long

    @Transaction
    @Query("SELECT * FROM Score WHERE game_record_id=:recordId AND category_id=:categoryId")
    fun getScoresWithPlayers(recordId: Int, categoryId: Int): Flow<List<ScoreWithPlayer>>
}
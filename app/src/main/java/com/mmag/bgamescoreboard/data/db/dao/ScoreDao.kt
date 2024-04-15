package com.mmag.bgamescoreboard.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.mmag.bgamescoreboard.data.db.model.Score
import com.mmag.bgamescoreboard.data.db.model.ScoringCategory
import com.mmag.bgamescoreboard.data.db.model.relations.RecordWithCategories
import com.mmag.bgamescoreboard.data.db.model.relations.ScoreWithPlayer
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {
    @Insert
    suspend fun addScore(score: Score)

    @Query("SELECT *  FROM Score WHERE game_record_id=:recordId AND player_id=:playerId AND category_id=:categoryId")
    fun getScoreByRecordIdCategoryAndPlayer(recordId: Int, playerId:Int, categoryId: Int): Score?

    @Transaction
    @Query("SELECT * FROM Score WHERE game_record_id=:recordId AND category_id=:categoryId")
    fun getScoresWithPlayers(recordId: Int, categoryId: Int): Flow<List<ScoreWithPlayer>>

    @Query("DELETE FROM Score WHERE game_record_id=:gameRecord")
    fun deleteScoresByRecords(gameRecord: Int) :Int
}
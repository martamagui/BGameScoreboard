package com.mmag.bgamescoreboard.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mmag.bgamescoreboard.data.db.model.entities.Score
import com.mmag.bgamescoreboard.data.db.model.relations.ScoreWithPlayer
import com.mmag.bgamescoreboard.data.db.model.subsets.FrequentPlayer
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {
    @Insert
    suspend fun addScore(score: Score)

    @Update
    suspend fun updateScore(score: Score)

    @Query("SELECT *  FROM Score WHERE game_record_id=:recordId AND player_id=:playerId AND category_id=:categoryId")
    fun getScoreByRecordIdCategoryAndPlayer(recordId: Int, playerId: Int, categoryId: Int): Score?

    @Transaction
    @Query("SELECT * FROM Score WHERE game_record_id=:recordId AND category_id=:categoryId")
    fun getScoresWithPlayers(recordId: Int, categoryId: Int): Flow<List<ScoreWithPlayer>>

    @Transaction
    @Query("SELECT * FROM Score WHERE game_record_id=:recordId AND player_id=:playerId")
    fun getPlayersScoresFromRecord(recordId: Int, playerId: Int): Flow<List<Score>>


    @Query("DELETE FROM Score WHERE game_record_id=:gameRecord")
    fun deleteScoresByRecords(gameRecord: Int): Int

    @Query("SELECT player_id, COUNT(*) as count FROM (SELECT DISTINCT player_id, game_record_id FROM Score) GROUP BY player_id ORDER BY count DESC LIMIT 5")
    suspend fun getMostFrequentPlayers(): List<FrequentPlayer>
}
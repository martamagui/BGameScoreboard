package com.mmag.bgamescoreboard.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.mmag.bgamescoreboard.data.db.model.GameScoreRecord
import com.mmag.bgamescoreboard.data.db.model.relations.RecordWithCategories
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @Insert
    suspend fun addRecord(gameScoreRecord: GameScoreRecord): Long

    @Query("DELETE FROM GameScoreRecord WHERE board_game_id=:gameId")
    fun deleteRecordsByGame(gameId: Int):Int

    @Query("DELETE FROM GameScoreRecord WHERE id=:recordId")
    fun deleteRecordById(recordId: Int):Int

    @Transaction
    @Query("SELECT *  FROM GameScoreRecord WHERE id=:recordId")
    fun getRecordWithCategories(recordId: Int): Flow<RecordWithCategories?>

    @Transaction
    @Query("SELECT *  FROM GameScoreRecord WHERE board_game_id =:gameId")
    fun getRecordsByGameId(gameId: Int): Flow<RecordWithCategories?>
}
package com.mmag.bgamescoreboard.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mmag.bgamescoreboard.data.db.model.entities.BoardGame
import com.mmag.bgamescoreboard.data.db.model.relations.BoardGameWithGameRecordRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface BoardGameDao {

    @Query("SELECT * FROM BoardGame")
    fun getBoardGameList(): Flow<List<BoardGame>>

    @Query("SELECT * FROM BoardGame WHERE id = :id")
    fun getBoardGame(id: Int): Flow<BoardGame>

    @Transaction
    @Query("SELECT * FROM BoardGame WHERE id = :id")
    fun getBoardGameWithGameRecordRelation(id: Int): Flow<BoardGameWithGameRecordRelation?>

    @Insert
    suspend fun addGame(boardGame: BoardGame)

    @Transaction
    @Query("DELETE FROM BoardGame WHERE id = :id")
    fun deleteGame(id: Int): Int

    @Update
    suspend fun updateGame(boardGame: BoardGame)
}
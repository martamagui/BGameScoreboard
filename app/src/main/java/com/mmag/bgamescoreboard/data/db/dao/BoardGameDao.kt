package com.mmag.bgamescoreboard.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.mmag.bgamescoreboard.data.db.model.BoardGame
import com.mmag.bgamescoreboard.data.db.model.relations.BoardGameWithGameRecordRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface BoardGameDao {

    @Query("SELECT * FROM BoardGame")
    fun getBoardGameList(): Flow<List<BoardGame>>

    @Transaction
    @Query("SELECT * FROM BoardGame WHERE id = :id")
    fun getBoardGameById(id: Int): Flow<BoardGameWithGameRecordRelation>

    @Insert
    suspend fun addGame(boardGame: BoardGame)

    @Transaction
    @Query("DELETE FROM BoardGame WHERE id = :id")
    fun deleteGame(id: Int): Int
}
package com.mmag.bgamescoreboard.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mmag.bgamescoreboard.data.db.model.BoardGame
import kotlinx.coroutines.flow.Flow

@Dao
interface BoardGameDao {

    @Query("SELECT * FROM BoardGame")
    fun getBoardGameList(): Flow<List<BoardGame>>

    @Query("SELECT * FROM BoardGame WHERE id = :id ")
    fun getBoardGameById(id: Int): Flow<List<BoardGame>>

    @Insert
    suspend fun addGame(boardGame: BoardGame)

    @Delete
    suspend fun deleteGame(boardGame: BoardGame)
}
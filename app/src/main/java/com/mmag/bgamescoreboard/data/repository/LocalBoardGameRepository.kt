package com.mmag.bgamescoreboard.data.repository

import android.graphics.drawable.Drawable
import com.mmag.bgamescoreboard.data.db.model.BoardGame
import kotlinx.coroutines.flow.Flow

interface LocalBoardGameRepository {
    suspend fun getAllBoardGames(): Flow<List<BoardGame>?>
    suspend fun getBoardGames(id:Int): Flow<BoardGame>
    suspend fun addGame(name: String, picture: ByteArray)
}
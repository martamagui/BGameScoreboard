package com.mmag.bgamescoreboard.data.repository

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.mmag.bgamescoreboard.data.db.model.BoardGame
import com.mmag.bgamescoreboard.data.db.model.relations.BoardGameWithGameRecordRelation
import kotlinx.coroutines.flow.Flow

interface LocalBoardGameRepository {
    fun getAllBoardGames(): Flow<List<BoardGame>?>
    fun getBoardGames(id: Int): Flow<BoardGameWithGameRecordRelation>
    suspend fun addGame(name: String, picture: Bitmap)
}
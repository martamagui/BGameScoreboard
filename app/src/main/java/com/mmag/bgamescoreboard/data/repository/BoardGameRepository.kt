package com.mmag.bgamescoreboard.data.repository

import android.graphics.Bitmap
import com.mmag.bgamescoreboard.data.db.model.entities.BoardGame
import com.mmag.bgamescoreboard.data.db.model.relations.BoardGameWithGameRecordRelation
import kotlinx.coroutines.flow.Flow

interface BoardGameRepository {
    fun getAllBoardGames(): Flow<List<BoardGame>?>
    fun getBoardGame(id: Int): Flow<BoardGameWithGameRecordRelation?>
    suspend fun addGame(name: String, picture: Bitmap)
    suspend fun updateGame(boardGame: BoardGame)
    suspend fun deleteGame(gameId:Int)
    suspend fun markAsFavorite(gameId:Int, isFavourite:Boolean)
}
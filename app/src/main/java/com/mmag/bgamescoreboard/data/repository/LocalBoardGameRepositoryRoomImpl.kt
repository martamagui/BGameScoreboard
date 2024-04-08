package com.mmag.bgamescoreboard.data.repository

import android.graphics.Bitmap
import com.mmag.bgamescoreboard.data.db.BGSDatabase
import com.mmag.bgamescoreboard.data.db.model.BoardGame
import com.mmag.bgamescoreboard.data.db.model.relations.BoardGameWithGameRecordRelation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalBoardGameRepositoryRoomImpl @Inject constructor(
    private val database: BGSDatabase
) : LocalBoardGameRepository {
    override fun getAllBoardGames(): Flow<List<BoardGame>?> = database.boardGameDao().getBoardGameList()

    override fun getBoardGame(id: Int): Flow<BoardGameWithGameRecordRelation> = database.boardGameDao().getBoardGameById(id)

    override suspend fun addGame(name: String, picture: Bitmap) {
        val boardGame = BoardGame(0, name, picture)
        database.boardGameDao().addGame(boardGame)
    }
}
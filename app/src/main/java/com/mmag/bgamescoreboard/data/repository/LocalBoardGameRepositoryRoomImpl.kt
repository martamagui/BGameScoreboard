package com.mmag.bgamescoreboard.data.repository

import com.mmag.bgamescoreboard.data.db.BGSDatabase
import com.mmag.bgamescoreboard.data.db.model.BoardGame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalBoardGameRepositoryRoomImpl @Inject constructor(
    private val database: BGSDatabase
) : LocalBoardGameRepository {
    override suspend fun getAllBoardGames(): Flow<List<BoardGame>?> = flow {
        database.boardGameDao().getBoardGameList()
    }

    override suspend fun getBoardGames(id: Int): Flow<BoardGame> = flow {
        database.boardGameDao().getBoardGameById(id)
    }

    override suspend fun addGame(name: String, picture: ByteArray) {
        val boardGame = BoardGame(0, name)
        //TODO añadir la foto
        database.boardGameDao().addGame(boardGame)
    }
}
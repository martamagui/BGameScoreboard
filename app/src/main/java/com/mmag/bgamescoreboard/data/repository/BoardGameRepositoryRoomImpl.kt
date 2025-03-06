package com.mmag.bgamescoreboard.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.mmag.bgamescoreboard.data.db.BGSDatabase
import com.mmag.bgamescoreboard.data.db.model.BoardGame
import com.mmag.bgamescoreboard.data.db.model.relations.BoardGameWithGameRecordRelation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class BoardGameRepositoryRoomImpl @Inject constructor(
    private val database: BGSDatabase,
) : BoardGameRepository {
    override fun getAllBoardGames(): Flow<List<BoardGame>?> =
        database.boardGameDao().getBoardGameList()

    override fun getBoardGame(id: Int): Flow<BoardGameWithGameRecordRelation?> =
        database.boardGameDao().getBoardGameWithGameRecordRelation(id)

    override suspend fun addGame(name: String, picture: Bitmap) {
        val boardGame = BoardGame(0, name, picture)
        database.boardGameDao().addGame(boardGame)
    }

    override suspend fun updateGame(boardGame: BoardGame) {
        database.boardGameDao().updateGame(boardGame)
    }

    override suspend fun deleteGame(gameId: Int) {
        val records = database.recordDao().getRecordWithCategories(gameId).first()

        var deletedScores = 0
        if (records?.record?.id != null) {
            deletedScores = database.scoreDao().deleteScoresByRecords(records.record.id!!)
        }
        val deletedCategories = database.categoryDao().deleteCategoryByGame(gameId)
        val deletedRecords = database.recordDao().deleteRecordsByGame(gameId)
        val deletedGames = database.boardGameDao().deleteGame(gameId)

        logData(
            "deletedScores: ${deletedScores}, \n" +
                    "deletedCategories: ${deletedCategories}, \n" +
                    "deletedRecords: ${deletedRecords}, \n" +
                    "deletedGames: ${deletedGames} \n"
        )
    }

    override suspend fun markAsFavorite(gameId: Int, isFavourite: Boolean) {
        val game =
            database.boardGameDao().getBoardGame(gameId).first().copy(isFavorite = isFavourite)
        database.boardGameDao().updateGame(game)
    }


    /*region --- OTHERS ---*/
    private fun logData(msg: String) {
        Log.d("BoardGameRep", msg)
    }
    /*endregion --- OTHERS ---*/
}
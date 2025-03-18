package com.mmag.bgamescoreboard.data.repository

import android.util.Log
import com.mmag.bgamescoreboard.data.db.BGSDatabase
import com.mmag.bgamescoreboard.data.db.model.entities.Player
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalPlayerRepositoryRoomImpl @Inject constructor(
    val database: BGSDatabase
) : LocalPlayerRepository {
    override fun getSavedPlayers(): Flow<List<Player>> = database.playerDao().getPlayers()

    override suspend fun savePlayer(name: String) {
        val player = Player(0, name)
        Log.d("Jugador", "$player")
        database.playerDao().addPlayer(player)
    }
}
package com.mmag.bgamescoreboard.data.repository

import com.mmag.bgamescoreboard.data.db.model.Player
import kotlinx.coroutines.flow.Flow

interface LocalPlayerRepository {
    fun getSavedPlayers(): Flow<List<Player>>
    suspend fun savePlayer(name: String)
}
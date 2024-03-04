package com.mmag.bgamescoreboard.ui.screen.game_record.players_screen

import com.mmag.bgamescoreboard.data.db.model.BoardGame
import com.mmag.bgamescoreboard.data.db.model.Player
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.screen.game_record.categories_screen.CategoriesUiState

data class GameRecordsPlayersUiState(
    val status: UiStatus = UiStatus.UNKNOWN,
    val errorMessage: Int? = null,
    val data: List<Player> = listOf(),
    val selectedPlayers: List<Player> = listOf()
)

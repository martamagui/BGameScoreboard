package com.mmag.bgamescoreboard.ui.screen.game_record.players_screen

import com.mmag.bgamescoreboard.data.db.model.entities.Player
import com.mmag.bgamescoreboard.domain.model.PlayerModel
import com.mmag.bgamescoreboard.ui.model.UiStatus

data class GameRecordsPlayersUiState(
    val status: UiStatus = UiStatus.UNKNOWN,
    val errorMessage: Int? = null,
    val data: List<PlayerModel> = listOf(),
    val selectedPlayers: List<PlayerModel> = listOf()
)

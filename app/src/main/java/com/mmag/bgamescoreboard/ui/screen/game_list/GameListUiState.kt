package com.mmag.bgamescoreboard.ui.screen.game_list

import com.mmag.bgamescoreboard.data.db.model.BoardGame
import com.mmag.bgamescoreboard.ui.model.UiStatus

data class GameListUiState(
    val status: UiStatus = UiStatus.UNKNOWN,
    val errorMessage: Int? = null,
    val data: List<BoardGame>? = null
)

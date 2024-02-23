package com.mmag.bgamescoreboard.ui.screen.new_game

import com.mmag.bgamescoreboard.ui.model.UiStatus

data class NewGameUiState(
    val status: UiStatus = UiStatus.UNKNOWN,
    val errorMessage: Int? = null
)

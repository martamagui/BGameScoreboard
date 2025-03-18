package com.mmag.bgamescoreboard.ui.screen.game_record.categories_screen

import com.mmag.bgamescoreboard.data.db.model.entities.ScoringCategory
import com.mmag.bgamescoreboard.ui.model.UiStatus

data class CategoriesUiState(
    val status: UiStatus = UiStatus.UNKNOWN,
    val errorMessage: Int? = null,
    val data: List<ScoringCategory> = listOf(),
)
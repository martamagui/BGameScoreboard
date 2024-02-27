package com.mmag.bgamescoreboard.ui.screen.game_detail

import com.mmag.bgamescoreboard.data.db.model.relations.BoardGameWithGameRecordRelation
import com.mmag.bgamescoreboard.ui.model.UiStatus

data class GameDetailUIState(
    val status: UiStatus = UiStatus.UNKNOWN,
    val errorMessage: Int? = null,
    val data: BoardGameWithGameRecordRelation? = null
)
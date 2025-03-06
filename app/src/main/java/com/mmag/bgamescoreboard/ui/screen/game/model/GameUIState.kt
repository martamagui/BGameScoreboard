package com.mmag.bgamescoreboard.ui.screen.game.model

import com.mmag.bgamescoreboard.data.db.model.relations.BoardGameWithGameRecordRelation
import com.mmag.bgamescoreboard.ui.model.UiStatus

data class GameUIState(
    val status: UiStatus = UiStatus.UNKNOWN,
    val errorMessage: Int? = null,
    val data: BoardGameWithGameRecordRelation? = null
)
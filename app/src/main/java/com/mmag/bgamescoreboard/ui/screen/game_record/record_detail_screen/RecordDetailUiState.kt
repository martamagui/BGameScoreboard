package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen

import androidx.compose.runtime.Stable
import com.mmag.bgamescoreboard.data.db.model.relations.RecordWithCategories
import com.mmag.bgamescoreboard.data.db.model.relations.ScoreWithPlayer
import com.mmag.bgamescoreboard.ui.model.UiStatus

@Stable
data class RecordDetailUiState(
    val recordWithCategories: RecordWithCategories? = null,
    val scoresByPlayersAndCategories: HashMap<Int, List<ScoreWithPlayer>> = HashMap(),
    val status: UiStatus = UiStatus.UNKNOWN
)

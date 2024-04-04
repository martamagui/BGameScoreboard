package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen

import com.mmag.bgamescoreboard.data.db.model.ScoringCategory
import com.mmag.bgamescoreboard.data.db.model.relations.PlayerWithRecordList
import com.mmag.bgamescoreboard.ui.model.UiStatus

data class RecordDetailUiState(
    val categoriesList: List<ScoringCategory> = listOf(),
    val scoresByPlayersAndCategories: HashMap<Int, List<PlayerWithRecordList>> = HashMap(),
    val status: UiStatus = UiStatus.UNKNOWN
)

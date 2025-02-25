package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.RecordDetailViewModel

@Composable
fun RecordDetailEditContent(
    viewModel: RecordDetailViewModel,
) {
    val state by viewModel.uiState.collectAsState()
    var tabIndex by rememberSaveable { mutableIntStateOf(0) }
    val scores = state.scoresByPlayersAndCategories[
        state.recordWithCategories?.scoringCategories?.get(tabIndex)?.id
    ] ?: listOf()

    LazyColumn(
        modifier = Modifier.fillMaxSize().testTag("RecordDetailEditContent"),
        contentPadding = PaddingValues(4.dp)
    ) {
        item {
            ScrollableTabRow(
                selectedTabIndex = tabIndex,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            ) {
                state.recordWithCategories?.scoringCategories?.forEachIndexed { index, text ->
                    RecordDetailTab(tabIndex, index, { tabIndex = it }, text)
                }
            }
        }
        if (scores.isEmpty()) {
            item {
                RecordDetailEmptyScoresItem(Modifier.fillMaxWidth())
                //TODO add a quit button
            }
        }

        //Todo create edit items
    }
}

package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.RecordDetailUiState
import com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.RecordDetailViewModel

@Composable
fun RecordDetailContent(
    tabIndex: Int,
    state: RecordDetailUiState,
    viewModel: RecordDetailViewModel,
    selectTab: (tab: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
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
                    RecordDetailTab(tabIndex, index, selectTab, viewModel, text)
                }
            }
        }

        if (state.selectedCategoryIndex != null) {
            val scores = state.scoresByPlayersAndCategories[
                state.recordWithCategories?.scoringCategories?.get(tabIndex)?.id
            ] ?: listOf()
            if (scores.isNullOrEmpty()) {
                item {
                    Text(
                        text = stringResource(id = R.string.record_detail_category_added_later_than_record),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 12.dp),
                    )
                }
            }
            items(scores) { score ->
                RecordDetailItem(score, Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp))
            }
        }
    }
}
package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.RecordDetailEditViewModel
import com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.RecordDetailUiState

@Composable
fun RecordDetailEditContent(
    recordId: Int,
    editViewModel: RecordDetailEditViewModel = hiltViewModel<RecordDetailEditViewModel>(),
) {
    LaunchedEffect(rememberCoroutineScope()) {
        editViewModel.getCategories(recordId)
    }
    val state by editViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("RecordDetailEditContent")
    ) {
        when (state.status) {
            UiStatus.LOADING -> {}
            UiStatus.SUCCESS -> RecordDetailEditTabLayout(state, editViewModel)
            else -> RecordDetailEmptyScoresItem(Modifier.fillMaxWidth())
        }

    }
}

@Composable
private fun RecordDetailEditTabLayout(
    state: RecordDetailUiState,
    editViewModel: RecordDetailEditViewModel,
) {
    var tabIndex by rememberSaveable { mutableIntStateOf(0) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
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
                    RecordDetailTab(tabIndex, index, {
                        tabIndex = it
                    }, text)
                }
            }
        }

        if ((state.scoresByPlayersAndCategories[
                state.recordWithCategories?.scoringCategories?.get(tabIndex)?.id
            ] ?: listOf()).isEmpty()
        ) {
            item {
                RecordDetailEmptyScoresItem(Modifier.fillMaxWidth())
                //TODO add a quit button
            }
        }

        items(
            (state.scoresByPlayersAndCategories[
                state.recordWithCategories?.scoringCategories?.get(tabIndex)?.id
            ] ?: listOf()),
            key = { it.score.id }) { score ->
            RecordDetailEditItem(
                score.player.name,
                score.score.scoreAmount,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) { points ->
                editViewModel.updateScore(score.score.copy(scoreAmount = points))
            }
        }

        item {
            RecordDetailEditDisclaimerItem(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )
        }
    }
}


package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mmag.bgamescoreboard.ui.common.BGSToolbar
import com.mmag.bgamescoreboard.ui.model.UiStatus

@Composable
fun RecordDetailScreen(
    recordId: Int,
    viewModel: RecordDetailViewModel = hiltViewModel<RecordDetailViewModel>().also {
        it.getCategories(recordId)
    }
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            BGSToolbar(
                title = ""
            ) { }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {

            when (state.status) {
                UiStatus.LOADING -> {}
                UiStatus.SUCCESS -> {
                    ScrollableTabRow(selectedTabIndex = tabIndex) {
                        state.categoriesList.forEachIndexed { index, text ->
                            Tab(
                                selected = tabIndex == index,
                                onClick = { tabIndex = index }
                            ) {
                                Text(text = text.categoryName, modifier = Modifier.padding(8.dp))
                            }
                        }
                    }
                }

                UiStatus.ERROR -> {}
                UiStatus.EMPTY_RESPONSE -> {}
                UiStatus.UNKNOWN -> {}
            }
        }
    }
}
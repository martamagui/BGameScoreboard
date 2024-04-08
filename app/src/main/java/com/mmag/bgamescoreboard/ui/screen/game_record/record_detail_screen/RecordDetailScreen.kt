package com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.ui.common.BGSToolbar
import com.mmag.bgamescoreboard.ui.model.UiStatus

@Composable
fun RecordDetailScreen(
    recordId: Int,
    navController: NavController,
    viewModel: RecordDetailViewModel = hiltViewModel<RecordDetailViewModel>().also {
        it.getCategories(recordId)
    }
) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            BGSToolbar(
                title = state.recordWithCategories?.record?.date ?: ""
            ) { navController.popBackStack() }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                when (state.status) {
                    UiStatus.LOADING -> {}
                    UiStatus.SUCCESS -> {
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
                                        Tab(
                                            selected = tabIndex == index,
                                            onClick = {
                                                tabIndex = index
                                                viewModel.updateCategorySelected(index)
                                            }
                                        ) {
                                            Text(
                                                text = text.categoryName,
                                                modifier = Modifier.padding(8.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            if (state.selectedCategoryIndex != null) {
                                val scores = state.scoresByPlayersAndCategories[
                                    state.recordWithCategories?.scoringCategories?.get(tabIndex)?.id
                                ] ?: listOf()
                                items(scores) { score ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 24.dp, vertical = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = score.player.name)
                                        Text(text = score.score.scoreAmount.toString())
                                    }
                                }
                            }
                        }
                    }

                    UiStatus.ERROR -> {}
                    else -> {
                        Text(text = stringResource(id = R.string.record_detail_not_found_message))
                    }
                }
            }
        }
    }
}

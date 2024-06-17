package com.mmag.bgamescoreboard.ui.screen.game_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.data.db.model.relations.BoardGameWithGameRecordRelation
import com.mmag.bgamescoreboard.ui.common.BGSScrollableToolbar
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.navigation.BGSConfigRoutes
import com.mmag.bgamescoreboard.ui.screen.dialogs.DeleteBGameDialog
import com.mmag.bgamescoreboard.ui.screen.game_detail.components.GameDetailContentHeader
import com.mmag.bgamescoreboard.ui.screen.game_detail.components.GameDetailNotFound
import com.mmag.bgamescoreboard.ui.screen.game_detail.components.GameDetailRecordItem
import com.mmag.bgamescoreboard.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(
    gameId: Int,
    navController: NavController,
    viewModel: GameDetailViewModel = hiltViewModel<GameDetailViewModel>().also {
        it.getGameDetails(gameId)
    }
) {
    val state by viewModel.uiState.collectAsState()
    var shouldShowDialog by rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            BGSScrollableToolbar(
                title = state.data?.game?.name ?: "...",
                backAction = { navController.popBackStack() },
                action = { shouldShowDialog = true },
                scrollBehavior = scrollBehavior
            )
        }, floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(BGSConfigRoutes.Builder.newScoreStep(gameId.toString(), 1))
            }) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.game_detail_add_icon_description)
                )
            }
        }) { paddingValues ->
        Column() {
            if (shouldShowDialog) {
                DeleteBGameDialog(
                    onDismiss = { shouldShowDialog = false }) {
                    viewModel.deleteGame(gameId)
                    navController.popBackStack()
                }
            }
            when (state.status) {
                UiStatus.SUCCESS -> {
                    if (state.data != null) {
                        GameDetailContent(Modifier.fillMaxWidth(), state.data!!, navController)
                    }
                }

                UiStatus.ERROR -> GameDetailNotFound(
                    Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                )

                UiStatus.EMPTY_RESPONSE -> {}
                UiStatus.UNKNOWN -> {}
                UiStatus.LOADING -> LinearProgressIndicator(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun GameDetailContent(
    modifier: Modifier,
    data: BoardGameWithGameRecordRelation,
    navController: NavController
) {
    Column(modifier = modifier) {
        GameDetailContentHeader(data, Modifier.fillMaxWidth())
        if (!data.records.isNullOrEmpty()) {
            LazyColumn(
                contentPadding = PaddingValues(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(data.records.reversed()) { item ->
                    GameDetailRecordItem(item, Modifier.fillMaxWidth()) {
                        navController.navigate(BGSConfigRoutes.Builder.scoreRecordDetail(item.id))
                    }
                }
            }
        } else {
            Text(
                text = stringResource(id = R.string.game_detail_no_records_added),
                textAlign = TextAlign.Center,
                style = Typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            )
        }
    }
}
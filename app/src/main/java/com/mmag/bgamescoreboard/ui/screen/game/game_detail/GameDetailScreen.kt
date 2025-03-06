package com.mmag.bgamescoreboard.ui.screen.game.game_detail

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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.data.db.model.relations.BoardGameWithGameRecordRelation
import com.mmag.bgamescoreboard.ui.common.BGSScrollableToolbar
import com.mmag.bgamescoreboard.ui.common.ObserveAsEvents
import com.mmag.bgamescoreboard.ui.common.SnackBarAction
import com.mmag.bgamescoreboard.ui.common.SnackBarController
import com.mmag.bgamescoreboard.ui.common.SnackBarEvent
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.navigation.BGSConfigRoutes
import com.mmag.bgamescoreboard.ui.screen.dialogs.DeleteBGameDialog
import com.mmag.bgamescoreboard.ui.screen.game.components.GameDetailContentHeader
import com.mmag.bgamescoreboard.ui.screen.game.components.GameDetailNotFound
import com.mmag.bgamescoreboard.ui.screen.game.components.GameDetailRecordItem
import com.mmag.bgamescoreboard.ui.theme.Typography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailScreen(
    gameId: Int,
    navController: NavController,
    viewModel: GameDetailViewModel = hiltViewModel<GameDetailViewModel>().also {
        it.getGameDetails(gameId)
    },
) {
    val state by viewModel.uiState.collectAsState()
    var shouldShowDialog by rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val snackBarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()
    val context =LocalContext.current

    ObserveAsEvents(
        flow = SnackBarController.event,
        snackBarHostState
    ) { event ->
        scope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()
            val result = snackBarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.message,
                duration = SnackbarDuration.Short,
            )
            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.fillMaxWidth()
            )
        },
        topBar = {
            BGSScrollableToolbar(
                title = state.data?.game?.name ?: "...",
                backAction = { navController.popBackStack() },
                markAsFavouriteAction = {
                    viewModel.markAsFavourite(
                        gameId,
                        !(state.data?.game?.isFavorite ?: false)
                    )
                    val message: Int = if (state.data?.game?.isFavorite == true) {
                        R.string.game_detail_game_unmarked_favorite_message
                    } else {
                        R.string.game_detail_game_marked_favorite_message
                    }
                    scope.launch {
                        SnackBarController.sendEvent(
                            SnackBarEvent(
                                message = context.resources.getString(message),
                                action = SnackBarAction(
                                    message = context.resources.getString(R.string.game_detail_game_undo_favorite),
                                    action = {
                                        viewModel.markAsFavourite(
                                            gameId,
                                            !(state.data?.game?.isFavorite ?: false)
                                        )
                                    }
                                ))
                        )
                    }
                },
                deleteAction = { shouldShowDialog = true },
                editAction = {
                    navController.navigate(BGSConfigRoutes.Builder.gameEdit(gameId.toString()))
                },
                scrollBehavior = scrollBehavior,
                isFavorite = state.data?.game?.isFavorite ?: false
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
    navController: NavController,
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
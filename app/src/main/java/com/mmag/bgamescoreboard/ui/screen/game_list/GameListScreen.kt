package com.mmag.bgamescoreboard.ui.screen.game_list


import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.data.db.model.BoardGame
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.navigation.BGSConfigRoutes
import com.mmag.bgamescoreboard.ui.screen.dialogs.DeleteBGameDialog
import com.mmag.bgamescoreboard.ui.screen.game_list.components.GameListContentHeader
import com.mmag.bgamescoreboard.ui.screen.game_list.components.GameListItemBoardGame
import com.mmag.bgamescoreboard.ui.screen.game_list.components.GameListProgressIndicator
import com.mmag.bgamescoreboard.ui.screen.game_list.components.GameListScreenNewGameFAB
import com.mmag.bgamescoreboard.ui.screen.game_list.components.GameListScreenTopAppBar


@Composable
fun GameListScreen(
    viewModel: GameListViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var selectedGameToDelete: Int? by rememberSaveable {
        mutableStateOf(null)
    }
    Scaffold(
        floatingActionButton = { GameListScreenNewGameFAB(navHostController) },
        topBar = { GameListScreenTopAppBar() }
    ) { paddingValues ->
        if (showDeleteDialog) {
            DeleteBGameDialog(onDismiss = { showDeleteDialog = false }) {
                if (selectedGameToDelete != null) {
                    viewModel.deleteGame(selectedGameToDelete!!)
                    showDeleteDialog = false
                }
            }
        }
        if (uiState.status == UiStatus.LOADING) {
            GameListProgressIndicator(Modifier.fillMaxWidth())
        } else {
            GameListContent(
                paddingValues,
                uiState,
                modifier = Modifier.fillMaxSize(),
                naviGateToGameDetail = { game ->
                    val route = BGSConfigRoutes.Builder.gameDetail("${game.id}")
                    navHostController.navigate(route)
                }, deleteGame = { game ->
                    selectedGameToDelete = game.id
                    showDeleteDialog = true
                }
            )
        }
    }
}

@Composable
private fun GameListContent(
    paddingValues: PaddingValues,
    uiState: GameListUiState,
    modifier: Modifier,
    naviGateToGameDetail: (boardGame: BoardGame) -> Unit,
    deleteGame: (boardGame: BoardGame) -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier
            .padding(paddingValues)
            .padding(12.dp)
    ) {
        if (uiState.status == UiStatus.SUCCESS) {
            LazyColumn(
                contentPadding = PaddingValues(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.data != null) {
                    item { GameListContentHeader(uiState, Modifier.fillMaxWidth()) }
                }

                item {
                    Text(
                        text = stringResource(id = R.string.game_list_screen_sub_title),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp, bottom = 12.dp)
                    )
                }

                if (uiState.data != null) {
                    items(uiState.data!!) { boardGame ->
                        GameListItemBoardGame(
                            { naviGateToGameDetail(boardGame) },
                            { deleteGame(boardGame) },
                            boardGame,
                            Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth()
                                .heightIn(min = 120.dp)
                        )
                    }
                }
            }
        } else {
            Text(
                text = stringResource(id = R.string.game_list_no_games_found_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}



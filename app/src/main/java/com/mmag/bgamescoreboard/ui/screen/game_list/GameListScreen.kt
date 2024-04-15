package com.mmag.bgamescoreboard.ui.screen.game_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissState
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.data.db.model.BoardGame
import com.mmag.bgamescoreboard.ui.common.SwipeableItemBackground
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.navigation.BGSConfigRoutes
import com.mmag.bgamescoreboard.ui.screen.dialogs.DeleteBGameDialog
import com.mmag.bgamescoreboard.ui.theme.vertGradShadow
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
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
        floatingActionButton = { NewGameFAB(navHostController) },
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.game_list_screen_title),
                    fontSize = 32.sp,
                    modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
                )
            })
        }
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
            GameListProgressScreen(Modifier.fillMaxWidth())
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
                    items(uiState.data!!) { boardGame ->
                        ItemBoardGame(
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

@Composable
private fun GameListProgressScreen(modifier: Modifier) {
    LinearProgressIndicator(modifier = modifier)
    Text(
        text = stringResource(id = R.string.game_list_searching_games_title),
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun NewGameFAB(navHostController: NavHostController) {
    FloatingActionButton(
        onClick = { navHostController.navigate(BGSConfigRoutes.NEW_GAME) },
        modifier = Modifier
            .padding(24.dp)
    ) {
        Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.game_list_add))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemBoardGame(
    onClickAction: () -> Unit,
    onDismiss: () -> Unit,
    boardGame: BoardGame,
    modifier: Modifier
) {
    var show by remember { mutableStateOf(true) }
    var resetState by remember { mutableStateOf(false) }

    var dismissState = rememberDismissState(
        confirmValueChange = {
            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                show = false
                !resetState
                true
            } else false
        }, positionalThreshold = { 100.dp.toPx() }
    )

    ElevatedCard(
        modifier = modifier,
        onClick = { onClickAction() }
    ) {
        SwipeToDismiss(
            state = dismissState,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { state ->
                    if (!state.hasFocus) {
                        resetState = true
                    }
                },
            background = { SwipeableItemBackground(dismissState = dismissState) },
            directions = setOf(DismissDirection.StartToEnd),
            dismissContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(shape = RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        bitmap = boardGame.image.asImageBitmap(),
                        contentDescription = boardGame.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Box(
                        modifier = Modifier
                            .background(vertGradShadow)
                            .fillMaxSize()
                            .alpha(0.2f)
                    )
                    Text(
                        text = boardGame.name,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        )
    }

    LaunchedEffect(show) {
        delay(200)
        if (!show) {
            onDismiss()
            dismissState.reset()
        }
        if (resetState) {
            dismissState.reset()
            resetState = false
        }
    }
}

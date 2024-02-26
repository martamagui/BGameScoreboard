package com.mmag.bgamescoreboard.ui.screen.game_list

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.data.db.model.BoardGame
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.navigation.BGSConfigRoutes


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GameListScreen(
    viewModel: GameListViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(floatingActionButton = { NewGameFAB(navHostController) }) { paddingValues ->
        if (uiState.status == UiStatus.LOADING) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            Text(
                text = stringResource(id = R.string.game_list_searching_games_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                textAlign = TextAlign.Center
            )
        } else {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
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
                                    {
                                        val route =
                                            BGSConfigRoutes.Builder.gameDetail("${boardGame.id}")
                                        navHostController.navigate(route)
                                    },
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

    }
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
    boardGame: BoardGame,
    modifier: Modifier
) {
    ElevatedCard(modifier = modifier,
        onClick = { onClickAction() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                bitmap = boardGame.image.asImageBitmap(),
                contentDescription = boardGame.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(text = boardGame.name, Modifier.padding(8.dp))
        }
    }
}

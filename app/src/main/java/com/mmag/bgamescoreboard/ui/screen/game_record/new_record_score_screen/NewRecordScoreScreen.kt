package com.mmag.bgamescoreboard.ui.screen.game_record.new_record_score_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.ui.common.BGSToolbar
import com.mmag.bgamescoreboard.ui.model.PlayerWithScore
import com.mmag.bgamescoreboard.ui.navigation.BGSConfigRoutes
import com.mmag.bgamescoreboard.ui.screen.game_record.players_screen.GameRecordPlayersViewModel

@Composable
fun NewRecordScoreScreen(
    navController: NavController,
    gameId: Int,
    viewModel: GameRecordPlayersViewModel = if (
        getViewModelReference(navController, gameId) != null
    ) {
        hiltViewModel(getViewModelReference(navController, gameId)!!)
    } else {
        hiltViewModel()
    },
    step: Int
) {
    val categoryState by viewModel.categoriesUiState.collectAsState()
    val playerState by viewModel.playersUIState.collectAsState()
    val pattern = remember { Regex("^\\d+\$") }

    if (categoryState.data.size > (step - 3)) {
        Scaffold(
            topBar = {
                BGSToolbar(
                    title = stringResource(
                        id = R.string.record_score_screen_title,
                        categoryState.data[step - 3].categoryName
                    )
                ) { navController.popBackStack() }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(bottom = 32.dp, start = 32.dp, end = 32.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                if (!playerState.selectedPlayers.isNullOrEmpty()) {
                    LazyColumn() {
                        items(playerState.selectedPlayers) { player ->
                            var score by rememberSaveable { mutableStateOf("") }
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = player.name,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )
                                TextField(
                                    value = score,
                                    maxLines = 1,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    onValueChange = {
                                        if (it.isEmpty() || it.matches(pattern)) {
                                            score = it
                                            try {
                                                viewModel.updatePlayerScoreValue(
                                                    categoryState.data[step - 3].id,
                                                    PlayerWithScore(player.id, score.toInt())
                                                )
                                            } catch (e: NumberFormatException) {
                                                e.printStackTrace()
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp)
                                )
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp)
                ) {
                    Button(onClick = {
                        if (categoryState.data.size <= step - 2) {
                            viewModel.saveScoreRecord() {
                                // navController.popBackStack(BGSConfigRoutes.HOME, true)
                                //TODO navegar a un redirect
                                navController.navigate(BGSConfigRoutes.Builder.gameDetail(gameId.toString())) {
                                    popUpTo(BGSConfigRoutes.HOME) { inclusive = false }
                                }
                            }
                        } else {
                            navController.navigate(
                                BGSConfigRoutes.Builder.newScoreStep(gameId.toString(), step + 1)
                            )
                        }
                    }, modifier = Modifier.fillMaxWidth()) {
                        Text(text = stringResource(id = R.string.record_score_screen_save_scores_button))
                    }
                }
            }
        }
    } else {
        Scaffold(
            topBar = {
                BGSToolbar(
                    title = stringResource(
                        id = R.string.error_category_not_found
                    )
                ) { }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(32.dp)
                    .fillMaxSize()
            ) {
                Button(onClick = {
                    viewModel.saveScoreRecord() {
                        navController.navigate(BGSConfigRoutes.Builder.gameDetail(gameId.toString()))
                    }
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(id = R.string.record_score_category_not_found_button))
                }
            }
        }
    }


}

fun getViewModelReference(navController: NavController, gameId: Int): ViewModelStoreOwner? {
    return try {
        navController.getBackStackEntry(
            BGSConfigRoutes.Builder.newScoreStep(gameId.toString(), 1)
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

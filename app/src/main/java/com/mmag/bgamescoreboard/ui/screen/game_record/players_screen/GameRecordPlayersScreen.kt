package com.mmag.bgamescoreboard.ui.screen.game_record.players_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.domain.model.PlayerModel
import com.mmag.bgamescoreboard.ui.common.BGSToolbar
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.navigation.BGSConfigRoutes
import com.mmag.bgamescoreboard.ui.theme.Typography
import com.mmag.bgamescoreboard.utils.capitalizeFirstLetter

@Composable
fun GameRecordPlayersScreen(
    gameId: Int,
    navController: NavController,
    viewModel: GameRecordPlayersViewModel = hiltViewModel<GameRecordPlayersViewModel>().apply {
        this.gameId = gameId
    },
) {
    val uiState by viewModel.playersUIState.collectAsState()
    var userName by rememberSaveable { mutableStateOf("") }

    Scaffold(topBar = {
        BGSToolbar(
            title = stringResource(id = R.string.players_screen_title)
        ) { navController.popBackStack() }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = userName,
                        placeholder = { Text(text = stringResource(id = R.string.players_screen_hint)) },
                        onValueChange = {
                            if (it.contains("\n")) {
                                val text = it.trim().replace("\r", "").replace("\n", "")
                                if (text.isNotEmpty()) {
                                    viewModel.savePlayer(userName)
                                    userName = ""
                                }
                            } else {
                                userName = it.capitalizeFirstLetter()
                            }
                        },
                        modifier = Modifier.fillMaxWidth(0.85f)
                    )

                    FilledIconButton(
                        onClick = {
                            viewModel.savePlayer(userName)
                            userName = ""
                        },
                        enabled = userName.trim().isNotEmpty()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add, contentDescription = stringResource(
                                id = R.string.categories_screen_category_description
                            ), modifier = Modifier.size(24.dp)
                        )
                    }
                }
                GameRecordSavedPlayers(Modifier.fillMaxWidth(), uiState) { player ->
                    viewModel.addDeletePlayer(player)
                }
            }
            Column(Modifier.fillMaxWidth()) {
                Button(onClick = {
                    if (uiState.selectedPlayers.isNotEmpty()) {
                        viewModel.getCategories(gameId)
                        navController.navigate(
                            BGSConfigRoutes.Builder.newScoreStep(gameId.toString(), 2)
                        )
                    }
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(id = R.string.players_screen_button_text))
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}


@Composable
fun GameRecordSavedPlayers(
    modifier: Modifier,
    uiState: GameRecordsPlayersUiState,
    addDeletePlayer: (PlayerModel) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(vertical = 24.dp)
            .testTag("GameRecordSavedPlayers")
    ) {
        if (uiState.status == UiStatus.SUCCESS && uiState.data.isNotEmpty()) {
            GameRecordPlayersGrid(
                modifier = Modifier.fillMaxWidth(),
                players = uiState.data,
                selectedPlayers = uiState.selectedPlayers,
                addDeletePlayer = addDeletePlayer
            )
            if (uiState.selectedPlayers.isEmpty()) {
                GameRecordPlayerSelectionMessage(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
            }
        } else {
            Text(
                text = stringResource(id = R.string.players_screen_no_players_found),
                style = Typography.bodyMedium,
                modifier = Modifier
                    .testTag("GameRecordNoPlayersFound")
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
fun GameRecordPlayersGrid(
    modifier: Modifier,
    players: List<PlayerModel>,
    selectedPlayers: List<PlayerModel>,
    addDeletePlayer: (PlayerModel) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(3),
        verticalItemSpacing = 2.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.testTag("GameRecordPlayersGrid")
    ) {
        itemsIndexed(players, key = { index: Int, item: PlayerModel -> item.id }) { index, player ->
            FilterChip(
                selected = selectedPlayers.contains(player),
                onClick = { addDeletePlayer(player) },
                trailingIcon = {
                    if (player.isFrequent) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = stringResource(id = R.string.players_screen_frequent_player_icon_description),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                },
                label = {
                    Text(
                        text = player.name,
                        fontWeight = if (player.isFrequent) FontWeight.Bold else FontWeight.Normal,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp, end = 8.dp, bottom = 6.dp)
                    )
                }, modifier = Modifier.padding(0.dp)
            )
        }
    }
}

@Composable
fun GameRecordPlayerSelectionMessage(modifier: Modifier) {
    Card(modifier = modifier.testTag("GameRecordPlayerSelectionMessage")) {
        Text(
            text = stringResource(id = R.string.players_screen_no_players_selected),
            style = Typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

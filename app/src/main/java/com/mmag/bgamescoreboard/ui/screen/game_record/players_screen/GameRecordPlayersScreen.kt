package com.mmag.bgamescoreboard.ui.screen.game_record.players_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.ui.common.BGSToolbar
import com.mmag.bgamescoreboard.ui.model.UiStatus
import com.mmag.bgamescoreboard.ui.theme.Typography
import java.nio.file.WatchEvent

@Composable
fun GameRecordPlayersScreen(
    gameId: Int,
    navController: NavController,
    viewModel: GameRecordPlayersViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
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
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextField(value = userName,
                        placeholder = { Text(text = stringResource(id = R.string.players_screen_hint))}
                        ,onValueChange = {
                        if (it.contains("\n")) {
                            viewModel.savePlayer(userName)
                            userName = ""
                        } else {
                            userName = it
                        }

                    }, modifier = Modifier.fillMaxWidth())
                }
                GameRecordSavedPlayers(Modifier.fillMaxWidth(), viewModel)
            }
            Column(Modifier.fillMaxWidth()) {
                Button(onClick = {
                    //TODO navegar a la siguiente pantalla
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(id = R.string.players_screen_button_text))
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameRecordSavedPlayers(modifier: Modifier, viewModel: GameRecordPlayersViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    Column(modifier = modifier.padding(vertical = 24.dp)) {
        if (uiState.status == UiStatus.SUCCESS && !uiState.data.isNullOrEmpty()) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(3),
                verticalItemSpacing = 2.dp,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                itemsIndexed(uiState.data) { index, player ->
                    InputChip(
                        selected = uiState.selectedPlayers.contains(player),
                        onClick = { viewModel.addDeletePlayer(player) },
                        label = {
                            Text(
                                text = player.name,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                modifier = Modifier
                                    .padding(4.dp)
                            )
                        }, modifier = Modifier
                    )
                }
            }
        } else {
            Text(
                text = stringResource(id = R.string.players_screen_no_players_found),
                style = Typography.bodyMedium
            )
        }
    }
}

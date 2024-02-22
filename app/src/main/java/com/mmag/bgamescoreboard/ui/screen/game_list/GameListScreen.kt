package com.mmag.bgamescoreboard.ui.screen.game_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mmag.bgamescoreboard.R
import com.mmag.bgamescoreboard.data.db.model.BoardGame
import com.mmag.bgamescoreboard.ui.navigation.BGSConfigRoutes


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GameListScreen(
    viewModel: GameListViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val boardGameList by viewModel.boardGames.collectAsState()

    Scaffold(floatingActionButton = { NewGameFAB(navHostController) }) { paddingValues ->
        BoxWithConstraints(modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)) {
            LazyColumn(contentPadding = PaddingValues(4.dp), modifier = Modifier.fillMaxWidth()) {
                items(boardGameList) { boardGame ->
                    ItemBoardGame(
                        navHostController, boardGame,
                        Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                            .heightIn(min = 120.dp)
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

@Composable
fun ItemBoardGame(
    navHostController: NavHostController,
    boardGame: BoardGame,
    modifier: Modifier
) {
    ElevatedCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = boardGame.name)
        }
    }
}

package com.mmag.bgamescoreboard.ui.screen.game_list

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mmag.bgamescoreboard.data.db.model.BoardGame


@Composable
fun GameListScreen(
    viewModel: GameListViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val boardGameList by viewModel.boardGames.collectAsState()
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        LazyColumn(contentPadding = PaddingValues(4.dp), modifier = Modifier.fillMaxWidth()) {
            items(boardGameList) { boardGame ->
                ItemBoardGame(navHostController, boardGame, Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun ItemBoardGame(
    navHostController: NavHostController,
    boardGame: BoardGame,
    modifier: Modifier
) {
    ElevatedCard(modifier = modifier) {
        Column (modifier = Modifier.fillMaxWidth()){
            Text(text = boardGame.name)
        }

    }
}

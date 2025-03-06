package com.mmag.bgamescoreboard.ui.screen.game.game_edition

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun GameEditionScreen(
    gameId: Int,
    navController: NavController,
    viewModel: GameEditionViewModel = hiltViewModel<GameEditionViewModel>().also {
        it.getGameDetails(gameId)
    },
) {
}
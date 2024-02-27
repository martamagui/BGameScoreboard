package com.mmag.bgamescoreboard.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mmag.bgamescoreboard.ui.screen.game_detail.GameDetailScreen
import com.mmag.bgamescoreboard.ui.screen.game_list.GameListScreen
import com.mmag.bgamescoreboard.ui.screen.game_record.players_screen.GameRecordPlayersScreen
import com.mmag.bgamescoreboard.ui.screen.new_game.NewGameScreen

@Composable
fun BGSNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = BGSConfigRoutes.HOME
    ) {
        composable(route = BGSConfigRoutes.HOME) {
            GameListScreen(navHostController = navController)
        }

        composable(route = BGSConfigRoutes.NEW_GAME) {
            NewGameScreen(navController)
        }

        composable(route = BGSConfigRoutes.GAME,
            arguments = listOf(
                navArgument(BGSConfigRoutes.Args.gameId) { type = NavType.IntType }
            )) {
            val game = it.arguments?.getInt(BGSConfigRoutes.Args.gameId) ?: 0
            GameDetailScreen(game, navController)
        }

        composable(route = BGSConfigRoutes.NEW_SCORE_STEP_1,
            arguments = listOf(
                navArgument(BGSConfigRoutes.Args.gameId) { type = NavType.IntType }
            )
        ) {
            val game = it.arguments?.getInt(BGSConfigRoutes.Args.gameId) ?: 0
            if (it.arguments != null) {
                GameRecordPlayersScreen(game, navController)
            }
        }

        composable(route = BGSConfigRoutes.NEW_SCORE_STEP_2,
            arguments = listOf(
                navArgument(BGSConfigRoutes.Args.gameId) { type = NavType.StringType }
            )
        ) {
            if (it.arguments != null) {
                NewGameScreen(navController)
            }
        }
    }
}
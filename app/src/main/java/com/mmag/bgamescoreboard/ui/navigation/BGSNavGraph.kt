package com.mmag.bgamescoreboard.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mmag.bgamescoreboard.ui.screen.game_list.GameListScreen
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

        composable(route = BGSConfigRoutes.NEW_SCORE,
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
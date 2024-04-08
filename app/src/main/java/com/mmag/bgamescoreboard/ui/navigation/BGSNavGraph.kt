package com.mmag.bgamescoreboard.ui.navigation

import android.annotation.SuppressLint
import android.util.Log
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
import com.mmag.bgamescoreboard.ui.screen.game_record.categories_screen.CategoriesScreen
import com.mmag.bgamescoreboard.ui.screen.game_record.new_record_score_screen.NewRecordScoreScreen
import com.mmag.bgamescoreboard.ui.screen.game_record.record_detail_screen.RecordDetailScreen


@SuppressLint("UnrememberedGetBackStackEntry")
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
        composable(
            route = BGSConfigRoutes.SCORE_RECORD_DETAIL,
            arguments = listOf(
                navArgument(BGSConfigRoutes.Args.scoreRecordId) { type = NavType.IntType },
            )
        ) {
            val record = it.arguments?.getInt(BGSConfigRoutes.Args.scoreRecordId) ?: 0
            RecordDetailScreen(recordId = record, navController)
        }

        composable(route = BGSConfigRoutes.NEW_SCORE_STEP,
            arguments = listOf(
                navArgument(BGSConfigRoutes.Args.gameId) { type = NavType.IntType },
                navArgument(BGSConfigRoutes.Args.step) { type = NavType.IntType }
            )
        ) {
            val game = it.arguments?.getInt(BGSConfigRoutes.Args.gameId) ?: 0
            val step = it.arguments?.getInt(BGSConfigRoutes.Args.step) ?: 0
            Log.d("STEP ", "Paso: ${step}")
            when (step) {
                0 -> {
                    //TODO hacer la pantalla de guardando datos
                }

                1 -> GameRecordPlayersScreen(game, navController)
                2 -> CategoriesScreen(game, navController = navController)
                else -> NewRecordScoreScreen(navController, game, step = step)
            }
        }


    }
}
package com.mmag.bgamescoreboard.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmag.bgamescoreboard.ui.screen.game_list.GameListScreen

@Composable
fun BGSNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = BGSConfigRoutes.Builder.homeRoute()
    ) {
        composable(route = BGSConfigRoutes.Builder.homeRoute()) {
            GameListScreen(navHostController = navController)
        }
    }
}
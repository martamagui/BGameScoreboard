package com.mmag.bgamescoreboard.ui.navigation

object BGSConfigRoutes {
    private const val HOME = "HOME"
    private const val GAME = "GAME/{game_id}"
    private const val NEW_GAME = "NEW_GAME"
    private const val NEW_USER = "NEW_USER"
    private const val NEW_SCORE = "NEW_SCORE/{game_id}"

    object Builder {
        fun homeRoute() = HOME

        fun gameRoute(gameId: String) = GAME.replace("{game_id}", gameId)
        fun newGameRoute() = NEW_GAME
        fun newUser() = NEW_USER
        fun newScore(gameId: String) = NEW_SCORE.replace("{game_id}", gameId)
    }
}


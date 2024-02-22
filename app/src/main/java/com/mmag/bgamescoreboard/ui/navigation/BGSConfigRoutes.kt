package com.mmag.bgamescoreboard.ui.navigation

object BGSConfigRoutes {
    const val HOME = "HOME"
    const val GAME = "GAME/{${Args.gameId}}"
    const val NEW_GAME = "NEW_GAME"
    const val NEW_USER = "NEW_USER"
    const val NEW_SCORE = "GAME/{${Args.gameId}}/NEW_SCORE"

    object Builder {
        fun newGame() = NEW_GAME
        fun newUser() = NEW_USER
        fun gameDetail(gameId: String) = GAME.replace("{${Args.gameId}}", gameId)
        fun newScore(gameId: String) = NEW_SCORE.replace("{${Args.gameId}}", gameId)
    }

    object Args {
        const val gameId = "game_id"
    }
}


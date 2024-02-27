package com.mmag.bgamescoreboard.ui.navigation

object BGSConfigRoutes {
    const val HOME = "HOME"
    const val GAME = "GAME/{${Args.gameId}}"
    const val NEW_GAME = "NEW_GAME"
    const val NEW_USER = "NEW_USER"
    const val NEW_SCORE_STEP_1 = "GAME/{${Args.gameId}}/NEW_SCORE/1"
    const val NEW_SCORE_STEP_2 = "GAME/{${Args.gameId}}/NEW_SCORE/2"

    object Builder {
        fun gameDetail(gameId: String) = GAME.replace("{${Args.gameId}}", gameId)
        fun newScoreStep1(gameId: String) = NEW_SCORE_STEP_1.replace("{${Args.gameId}}", gameId)
        fun newScoreStep2(gameId: String) = NEW_SCORE_STEP_2.replace("{${Args.gameId}}", gameId)
    }

    object Args {
        const val gameId = "game_id"
    }
}


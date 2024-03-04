package com.mmag.bgamescoreboard.ui.navigation

object BGSConfigRoutes {
    const val HOME = "HOME"
    const val GAME = "GAME/{${Args.gameId}}"
    const val NEW_GAME = "NEW_GAME"
    const val NEW_USER = "NEW_USER"
    const val NEW_SCORE_BASE = "GAME/{${Args.gameId}}/NEW_SCORE"
    const val NEW_SCORE_STEP = "GAME/{${Args.gameId}}/NEW_SCORE/{${Args.step}}"

    object Builder {
        fun gameDetail(gameId: String) = GAME.replace("{${Args.gameId}}", gameId)
        fun newScoreStep(gameId: String, step: Int) = NEW_SCORE_STEP
            .replace("{${Args.gameId}}", gameId)
            .replace("{${Args.step}}", step.toString())
    }

    object Args {
        const val gameId = "game_id"
        const val step = "step"
    }
}


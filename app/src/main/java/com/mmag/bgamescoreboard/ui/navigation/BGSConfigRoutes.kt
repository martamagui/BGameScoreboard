package com.mmag.bgamescoreboard.ui.navigation

object BGSConfigRoutes {
    const val HOME = "HOME"
    const val GAME = "GAME/{${Args.gameId}}"
    const val GAME_EDIT = "GAME/EDIT/{${Args.gameId}}"
    const val NEW_GAME = "NEW_GAME"
    const val SCORE_RECORD_DETAIL = "SCORE_RECORD_DETAIL/{${Args.scoreRecordId}}"
    const val NEW_SCORE_STEP = "GAME/{${Args.gameId}}/NEW_SCORE/{${Args.step}}"

    object Builder {
        fun gameDetail(gameId: String) = GAME.replace("{${Args.gameId}}", gameId)
        fun gameEdit(gameId: String) = GAME_EDIT.replace("{${Args.gameId}}", gameId)
        fun scoreRecordDetail(recordId: Int) =
            SCORE_RECORD_DETAIL.replace("{${Args.scoreRecordId}}", recordId.toString())

        fun newScoreStep(gameId: String, step: Int) = NEW_SCORE_STEP
            .replace("{${Args.gameId}}", gameId)
            .replace("{${Args.step}}", step.toString())
    }

    object Args {
        const val gameId = "game_id"
        const val scoreRecordId = "score_record_id"
        const val step = "step"
    }
}


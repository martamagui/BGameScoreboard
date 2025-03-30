package com.mmag.bgamescoreboard.ui.navigation


import org.junit.Test

class BGSConfigRoutesTest {

    @Test
    fun `Builder gameDetail should return GAME with gameId`() {
        val gameId = "1"
        val result = BGSConfigRoutes.Builder.gameDetail(gameId)
        assert(result == "GAME/1")
    }

    @Test
    fun `Builder gameEdit should return GAME_EDIT with gameId`() {
        val gameId = "1"
        val result = BGSConfigRoutes.Builder.gameEdit(gameId)
        assert(result == "GAME/EDIT/1")
    }

    @Test
    fun `Builder scoreRecordDetail should return SCORE_RECORD_DETAIL with recordId`() {
        val recordId = 1
        val result = BGSConfigRoutes.Builder.scoreRecordDetail(recordId)
        assert(result == "SCORE_RECORD_DETAIL/1")
    }

    @Test
    fun `Builder newScoreStep should return NEW_SCORE_STEP with gameId and step`() {
        val gameId = "1"
        val step = 1
        val result = BGSConfigRoutes.Builder.newScoreStep(gameId, step)
        assert(result == "GAME/1/NEW_SCORE/1")
    }
}
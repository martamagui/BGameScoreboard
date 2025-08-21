package com.mmag.bgamescoreboard.utils

import com.mmag.bgamescoreboard.data.db.model.entities.Score
import com.mmag.bgamescoreboard.data.db.model.relations.ScoreWithPlayer

fun getScoreWithPlayer(
    item: ScoreWithPlayer,
    recordId: Int,
    totalScore: Double,
) = ScoreWithPlayer(
    Score(
        0, item.player.id,
        recordId,
        0,
        totalScore
    ),
    item.player
)
package com.mmag.bgamescoreboard.data.db.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.mmag.bgamescoreboard.data.db.model.Player
import com.mmag.bgamescoreboard.data.db.model.Score

data class ScoreWithPlayer(
    @Embedded
    val score: Score,
    @Relation(
        parentColumn = "player_id",
        entityColumn = "id"
    )
    val player: Player
)

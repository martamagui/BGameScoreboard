package com.mmag.bgamescoreboard.data.db.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.mmag.bgamescoreboard.data.db.model.entities.Player
import com.mmag.bgamescoreboard.data.db.model.entities.Score

data class ScoreWithPlayer(
    @Embedded
    val score: Score,
    @Relation(
        parentColumn = "player_id",
        entityColumn = "id"
    )
    val player: Player
)

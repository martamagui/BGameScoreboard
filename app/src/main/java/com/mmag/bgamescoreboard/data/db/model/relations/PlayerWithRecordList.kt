package com.mmag.bgamescoreboard.data.db.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.mmag.bgamescoreboard.data.db.model.entities.Player
import com.mmag.bgamescoreboard.data.db.model.entities.Score


data class PlayerWithRecordList(
    @Embedded val player: Player,
    @Relation(
        parentColumn = "id",
        entityColumn = "player_id"
    )
    val scoreList: List<Score>
)

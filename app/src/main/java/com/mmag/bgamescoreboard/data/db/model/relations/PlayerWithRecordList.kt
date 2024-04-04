package com.mmag.bgamescoreboard.data.db.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.mmag.bgamescoreboard.data.db.model.Player
import com.mmag.bgamescoreboard.data.db.model.Score


data class PlayerWithRecordList(
    @Embedded val player: Player,
    @Relation(
        parentColumn = "id",
        entityColumn = "player_id"
    )
    val scoreList: List<Score>
)

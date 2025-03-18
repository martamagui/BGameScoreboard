package com.mmag.bgamescoreboard.data.db.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.mmag.bgamescoreboard.data.db.model.entities.GameScoreRecord
import com.mmag.bgamescoreboard.data.db.model.entities.ScoringCategory

data class RecordWithCategories(
    @Embedded val record: GameScoreRecord,
    @Relation(
        parentColumn = "board_game_id",
        entityColumn = "game_id"
    )
    val scoringCategories: List<ScoringCategory>
)

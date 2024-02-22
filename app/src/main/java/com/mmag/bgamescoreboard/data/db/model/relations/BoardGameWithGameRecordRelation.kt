package com.mmag.bgamescoreboard.data.db.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.mmag.bgamescoreboard.data.db.model.BoardGame
import com.mmag.bgamescoreboard.data.db.model.GameRecord


data class BoardGameWithGameRecordRelation(
    @Embedded
    val game: BoardGame,
    @Relation(
        parentColumn = "id",
        entityColumn = "board_game_id"
    )
    val records: List<GameRecord>
)
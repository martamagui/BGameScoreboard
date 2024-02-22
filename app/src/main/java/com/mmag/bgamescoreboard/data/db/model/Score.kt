package com.mmag.bgamescoreboard.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Score(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("player_id")
    val playerId: Int,
    @ColumnInfo("game_record_id")
    val gameRecordId: Int,
    @ColumnInfo("category_id")
    val categoryId: Int,
    @ColumnInfo("score_amount")
    val scoreAmount: Int
)

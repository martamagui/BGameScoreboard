package com.mmag.bgamescoreboard.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ScoringCategory(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo("game_id")
    val gameId: Int,
    @ColumnInfo("category_name")
    val categoryName: String
)

package com.mmag.bgamescoreboard.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    @ColumnInfo("board_game_id")
    val boardGameId: Int
)

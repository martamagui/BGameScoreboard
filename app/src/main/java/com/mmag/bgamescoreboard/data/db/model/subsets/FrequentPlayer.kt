package com.mmag.bgamescoreboard.data.db.model.subsets

import androidx.room.ColumnInfo

data class FrequentPlayer(
    @ColumnInfo(name = "player_id") val playerId: Int,
)

package com.mmag.bgamescoreboard.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BoardGame(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)

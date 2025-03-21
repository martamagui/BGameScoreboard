package com.mmag.bgamescoreboard.data.db.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)

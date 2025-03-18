package com.mmag.bgamescoreboard.data.db.model.entities

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BoardGame(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val image: Bitmap,
    val isFavorite: Boolean = false
)

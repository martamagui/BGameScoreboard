package com.mmag.bgamescoreboard.domain.model

data class PlayerModel(
    val id: Int,
    val name: String,
    val isFrequent: Boolean = false,
)

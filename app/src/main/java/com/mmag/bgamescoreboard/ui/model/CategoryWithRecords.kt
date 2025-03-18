package com.mmag.bgamescoreboard.ui.model

import com.mmag.bgamescoreboard.data.db.model.entities.Player

data class CategoryWithRecords(
    val categoryId: Int,
    val savedScores: MutableList<PlayerWithScore>
)

data class PlayerWithScore(
    val playerId: Int,
    val score: Int
)

fun Player.toPlayerWithScore(): PlayerWithScore {
    return PlayerWithScore(this.id, 0)
}

fun List<Player>.toPlayerWithScore(): MutableList<PlayerWithScore> {
    val list = mutableListOf<PlayerWithScore>()
    this.forEach { player ->
        list.add(player.toPlayerWithScore())
    }
    return list
}
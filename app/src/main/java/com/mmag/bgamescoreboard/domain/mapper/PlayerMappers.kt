package com.mmag.bgamescoreboard.domain.mapper

import com.mmag.bgamescoreboard.data.db.model.entities.Player
import com.mmag.bgamescoreboard.domain.model.PlayerModel
import com.mmag.bgamescoreboard.ui.model.PlayerWithScore
import com.mmag.bgamescoreboard.ui.model.toPlayerWithScore


fun Player.toPlayerModel(): PlayerModel {
    return PlayerModel(this.id, this.name)
}

fun PlayerModel.toPlayer(): Player {
    return Player(this.id, this.name)
}

fun List<PlayerModel>.toPlayerWithScore(): MutableList<PlayerWithScore> {
    val list = mutableListOf<PlayerWithScore>()
    this.forEach { player ->
        list.add(player.toPlayer().toPlayerWithScore())
    }
    return list
}
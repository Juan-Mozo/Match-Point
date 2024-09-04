package com.juanimozo.matchpoint.domain.model

import com.juanimozo.matchpoint.data.database.entity.Player

data class PlayerModel(
    val id: Int = 0,
    val name: String = ""
) {
    fun toPlayerEntity(): Player {
        return Player(id, name)
    }
}

package com.juanimozo.matchpoint.domain.model

data class TeamModel(
    val player1: PlayerModel,
    val player2: PlayerModel? = null
)

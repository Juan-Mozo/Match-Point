package com.juanimozo.matchpoint.presentation.match.state

import com.juanimozo.matchpoint.domain.model.PlayerModel

data class PlayerState(
    val player: PlayerModel? = null,
    val nameSearch: String = "",
    val isListOfPlayersCollapsed: Boolean = false
)

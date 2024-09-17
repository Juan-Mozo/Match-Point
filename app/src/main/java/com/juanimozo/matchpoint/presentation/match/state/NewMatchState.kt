package com.juanimozo.matchpoint.presentation.match.state

import com.juanimozo.matchpoint.domain.model.PlayerModel
import com.juanimozo.matchpoint.util.MatchType

data class NewMatchState(
    val matchType: MatchType = MatchType.SINGLES,
    val player1: PlayerState = PlayerState(),
    val player1TextField: String = "",
    val player2: PlayerState = PlayerState(),
    val player2TextField: String = "",
    val player3: PlayerState = PlayerState(),
    val player3TextField: String = "",
    val player4: PlayerState = PlayerState(),
    val player4TextField: String = "",
    val courtName: String = "",
    val countPoints: Boolean = false,
    val playersFound: List<PlayerModel> = emptyList(),
    val newPlayerName: String = "",
    val showPopup: Boolean = false,
    val playerIndexNewPlayer: Int = 0
)

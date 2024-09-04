package com.juanimozo.matchpoint.presentation.match.state

import com.juanimozo.matchpoint.util.MatchType

data class FieldState(
    val matchType: MatchType = MatchType.SINGLES,
    val player1Name: String = "",
    val player2Name: String = "",
    val player3Name: String? = "",
    val player4Name: String? = "",
    val courtName: String = "",
    val countPoints: Boolean = false
)

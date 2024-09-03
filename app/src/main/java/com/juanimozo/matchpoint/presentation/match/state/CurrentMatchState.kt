package com.juanimozo.matchpoint.presentation.match.state

import com.juanimozo.matchpoint.util.Points
import com.juanimozo.matchpoint.util.Sets
import com.juanimozo.matchpoint.util.Teams

data class CurrentMatchState(
    val date: String = "",
    val simplifiedDate: String = "",
    val duration: Long = 0L,
    val team1Name: String = "",
    val team2Name: String = "",
    val courtName: String = "",
    val team1Points: Points = Points.Zero(),
    val team2Points: Points = Points.Zero(),
    val currentSetTeam1: Int = 0,
    val currentSetTeam2: Int = 0,
    val isTieBreak: Boolean = false,
    val team1GamesFirstSet: Int = 0,
    val team2GamesFirstSet: Int = 0,
    val winnerFirstSet: Teams? = null,
    val team1GamesSecondSet: Int = 0,
    val team2GamesSecondSet: Int = 0,
    val winnerSecondSet: Teams? = null,
    val team1GamesThirdSet: Int = 0,
    val team2GamesThirdSet: Int = 0,
    val winnerThirdSet: Teams? = null,
    val isMatchEnded: Boolean = false,
    val currentSet: Sets = Sets.FirstSet,
    val winnerTeam: Teams? = null
)

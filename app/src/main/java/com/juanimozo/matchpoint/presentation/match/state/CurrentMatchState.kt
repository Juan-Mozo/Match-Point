package com.juanimozo.matchpoint.presentation.match.state

import com.juanimozo.matchpoint.domain.model.MatchWithTeamsModel
import com.juanimozo.matchpoint.util.Points
import com.juanimozo.matchpoint.util.Sets
import com.juanimozo.matchpoint.util.Teams

data class CurrentMatchState(
    val match: MatchWithTeamsModel = MatchWithTeamsModel(),
    val team1Points: Points = Points.Zero(),
    val team2Points: Points = Points.Zero(),
    val currentSetTeam1: Int = 0,
    val currentSetTeam2: Int = 0,
    val isTieBreak: Boolean = false,
    val isMatchEnded: Boolean = false,
    val currentSet: Sets = Sets.FirstSet
)

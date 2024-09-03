package com.juanimozo.matchpoint.presentation.history.state

import com.juanimozo.matchpoint.data.database.entity.Match

data class PastMatchesState(
    val matches: List<Match> = emptyList(),
    val revealedCard: Match? = null
)
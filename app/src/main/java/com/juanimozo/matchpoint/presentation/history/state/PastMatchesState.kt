package com.juanimozo.matchpoint.presentation.history.state

import com.juanimozo.matchpoint.domain.model.MatchWithTeamsModel

data class PastMatchesState(
    val matches: List<MatchWithTeamsModel> = emptyList(),
    val revealedCard: MatchWithTeamsModel? = null
)
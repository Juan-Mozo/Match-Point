package com.juanimozo.matchpoint.presentation.history.state

import com.juanimozo.matchpoint.domain.model.MatchWithTeamsModel
import com.juanimozo.matchpoint.domain.model.PlayerModel

data class HistoryState(
    val match: MatchWithTeamsModel = MatchWithTeamsModel(),
    val matches: List<MatchWithTeamsModel> = emptyList(),
    val revealedCard: MatchWithTeamsModel? = null,
    val playerInFilter: PlayerModel? = null,
    val playerTextField: String = "",
    val playersFound: List<PlayerModel> = emptyList()
)
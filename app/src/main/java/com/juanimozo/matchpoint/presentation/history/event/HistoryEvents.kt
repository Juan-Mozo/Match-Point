package com.juanimozo.matchpoint.presentation.history.event

import com.juanimozo.matchpoint.domain.model.MatchWithTeamsModel
import com.juanimozo.matchpoint.domain.model.PlayerModel

sealed class HistoryEvents {
    class ExpandCard(val match: MatchWithTeamsModel) : HistoryEvents()
    class CollapseCard(val match: MatchWithTeamsModel) : HistoryEvents()
    class UpdatePlayerName(val name: String): HistoryEvents()
    class SelectPlayer(val player: PlayerModel?): HistoryEvents()
}
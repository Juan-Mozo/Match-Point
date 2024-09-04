package com.juanimozo.matchpoint.presentation.history.event

import com.juanimozo.matchpoint.domain.model.MatchWithTeamsModel

sealed class HistoryEvents {

    class ExpandCard(val match: MatchWithTeamsModel) : HistoryEvents()

    class CollapseCard(val match: MatchWithTeamsModel) : HistoryEvents()

}
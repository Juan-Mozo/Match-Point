package com.juanimozo.matchpoint.presentation.history.event

import com.juanimozo.matchpoint.data.database.entity.Match

sealed class HistoryEvents {

    class ExpandCard(val match: Match) : HistoryEvents()

    class CollapseCard(val match: Match) : HistoryEvents()

}
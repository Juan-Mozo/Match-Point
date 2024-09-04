package com.juanimozo.matchpoint.presentation.match.event

import com.juanimozo.matchpoint.util.MatchType

sealed class NewMatchEvents {
    class UpdateMatchType(val matchType: MatchType): NewMatchEvents()
    class UpdatePlayer1Name(val name: String): NewMatchEvents()
    class UpdatePlayer2Name(val name: String): NewMatchEvents()
    class UpdatePlayer3Name(val name: String): NewMatchEvents()
    class UpdatePlayer4Name(val name: String): NewMatchEvents()
    class UpdateCourtName(val name: String): NewMatchEvents()
    class UpdateCountPoints(val isSelected: Boolean): NewMatchEvents()
}

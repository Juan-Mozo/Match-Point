package com.juanimozo.matchpoint.presentation.match.event

import com.juanimozo.matchpoint.domain.model.PlayerModel
import com.juanimozo.matchpoint.util.MatchType

sealed class NewMatchEvents {
    class UpdateMatchType(val matchType: MatchType): NewMatchEvents()
    class UpdatePlayerName(val playerNumber: Int, val name: String): NewMatchEvents()
    class UpdateNewPlayerName(val name: String): NewMatchEvents()
    class SelectPlayer(val playerNumber: Int, val player: PlayerModel?): NewMatchEvents()
    class UpdateCourtName(val name: String): NewMatchEvents()
    class UpdateCountPoints(val isSelected: Boolean): NewMatchEvents()
    class ManageNewPlayerPopup(val showPupup: Boolean, val playerNumber: Int) : NewMatchEvents()
}

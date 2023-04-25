package com.juanimozo.matchpoint.presentation.event

sealed class NewMatchEvents {
    class UpdateTeam1Name(val name: String): NewMatchEvents()
    class UpdateTeam2Name(val name: String): NewMatchEvents()
    class UpdateCourtName(val name: String): NewMatchEvents()
    class UpdateCountPoints(val isSelected: Boolean): NewMatchEvents()
}

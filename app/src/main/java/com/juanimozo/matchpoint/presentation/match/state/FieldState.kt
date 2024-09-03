package com.juanimozo.matchpoint.presentation.match.state

data class FieldState(
    val team1Name: String = "",
    val team2Name: String = "",
    val courtName: String = "",
    val countPoints: Boolean = false
)

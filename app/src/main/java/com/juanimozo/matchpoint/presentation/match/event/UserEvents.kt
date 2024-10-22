package com.juanimozo.matchpoint.presentation.match.event

data class UserEvents(
    val snackbarMessage: String = "",
    val isMatchEnded: Boolean = false
)

package com.juanimozo.matchpoint.presentation.match.state

data class ChronometerState(
    val startTime: Long = 0L,
    val isRunning: Boolean = false,
    val elapsedTime: Long = 0L
)
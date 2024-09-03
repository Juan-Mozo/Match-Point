package com.juanimozo.matchpoint.presentation.match.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.juanimozo.matchpoint.presentation.match.MatchViewModel
import kotlinx.coroutines.delay

@Composable
fun Chronometer(viewModel: MatchViewModel) {

    LaunchedEffect(viewModel.chronometerState.value.isRunning) {
        while (viewModel.chronometerState.value.isRunning) {
            viewModel.updateChronometer(System.currentTimeMillis() - viewModel.chronometerState.value.startTime)
            delay(10)
        }
    }

    Text(
        text = formatElapsedTime(viewModel.chronometerState.value.elapsedTime),
        style = MaterialTheme.typography.subtitle1
    )

}

fun formatElapsedTime(elapsedTime: Long): String {
    val seconds = elapsedTime / 1000 % 60
    val minutes = elapsedTime / 1000 / 60 % 60
    val hours = elapsedTime / 1000 / 60 / 60

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}
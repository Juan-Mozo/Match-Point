package com.juanimozo.matchpoint.ui.util

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Set(
    title: String,
    isCurrentSet: Boolean,
    team1Games: Int,
    team1CurrentSetGames: Int = 0,
    team2Games: Int,
    team2CurrentSetGames: Int = 0
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (isCurrentSet) {
            // Team1 Games
            Text(
                text = team1CurrentSetGames.toString(),
                style = MaterialTheme.typography.h2
            )
            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1
            )
            // Team2 Games
            Text(
                text = team2CurrentSetGames.toString(),
                style = MaterialTheme.typography.h2
            )
        } else {
            // Team1 Games
            Text(
                text = team1Games.toString(),
                style = MaterialTheme.typography.subtitle1
            )
            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle2
            )
            // Team2 Games
            Text(
                text = team2Games.toString(),
                style = MaterialTheme.typography.subtitle1
            )
        }

    }
}
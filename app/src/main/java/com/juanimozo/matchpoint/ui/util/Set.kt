package com.juanimozo.matchpoint.ui.util

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juanimozo.matchpoint.util.Teams

@Composable
fun Set(
    title: String,
    isCurrentSet: Boolean,
    team1Games: String,
    team1CurrentSetGames: String = "",
    team2Games: String,
    team2CurrentSetGames: String = "",
    setWinnerTeam: Teams?
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
                text = team1CurrentSetGames,
                style = MaterialTheme.typography.h2
            )
            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1
            )
            // Team2 Games
            Text(
                text = team2CurrentSetGames,
                style = MaterialTheme.typography.h2
            )
        } else {
            // Team1 Games
            Text(
                text = team1Games,
                style = MaterialTheme.typography.subtitle1,
                color = Teams.getTeamColor(Teams.Team1(), winnerTeam = setWinnerTeam)
            )
            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle2
            )
            // Team2 Games
            Text(
                text = team2Games,
                style = MaterialTheme.typography.subtitle1,
                color = Teams.getTeamColor(Teams.Team2(), winnerTeam = setWinnerTeam)
            )
        }

    }
}
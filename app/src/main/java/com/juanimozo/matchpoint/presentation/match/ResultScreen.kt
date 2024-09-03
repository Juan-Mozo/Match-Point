package com.juanimozo.matchpoint.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.juanimozo.matchpoint.presentation.match.MatchViewModel
import com.juanimozo.matchpoint.navigation.Screens
import com.juanimozo.matchpoint.presentation.match.components.formatElapsedTime
import com.juanimozo.matchpoint.ui.theme.NavyBlue
import com.juanimozo.matchpoint.presentation.match.components.GenericButton
import com.juanimozo.matchpoint.ui.util.Set
import com.juanimozo.matchpoint.util.Teams

@Composable
fun ResultScreen(navController: NavController, viewModel: MatchViewModel) {

    val match = viewModel.currentMatchState.value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        // Date
        Text(
            text = viewModel.currentMatchState.value.date,
            style = MaterialTheme.typography.subtitle2
        )
        // Duration
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Duración: ${formatElapsedTime(viewModel.chronometerState.value.elapsedTime)} min.",
                style = MaterialTheme.typography.body1
            )
        }

        // Team Names
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(end = 8.dp),
                text = viewModel.fieldState.value.team1Name,
                style = MaterialTheme.typography.subtitle1,
                color = Teams.getTeamColor(Teams.Team1(), winnerTeam = match.winnerTeam),
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = viewModel.fieldState.value.team2Name,
                style = MaterialTheme.typography.subtitle1,
                color = Teams.getTeamColor(Teams.Team2(), winnerTeam = match.winnerTeam),
                overflow = TextOverflow.Ellipsis
            )
        }

        // First Set
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Set(
                title = "Primer Set",
                isCurrentSet = false,
                team1Games = match.team1GamesFirstSet.toString(),
                team2Games = match.team2GamesFirstSet.toString(),
                setWinnerTeam = match.winnerFirstSet
            )
            Set(
                title = "Segundo Set",
                isCurrentSet = false,
                team1Games = match.team1GamesSecondSet.toString(),
                team2Games = match.team2GamesSecondSet.toString(),
                setWinnerTeam = match.winnerSecondSet
            )
            Set(
                title = "Tercer Set",
                isCurrentSet = false,
                team1Games = match.team1GamesThirdSet.toString(),
                team2Games = match.team2GamesThirdSet.toString(),
                setWinnerTeam = match.winnerThirdSet
            )
        }

        if (viewModel.fieldState.value.courtName.isNotBlank()) {
            // Court Name
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Cancha",
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = viewModel.fieldState.value.courtName,
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }

        // Back to home menu
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            GenericButton(
                modifier = Modifier
                    .padding(horizontal = 64.dp)
                    .fillMaxWidth(),
                color = NavyBlue,
                onClick = { navController.navigate(Screens.Main.route) },
                text = "Inicio"
            )
        }
    }
}
package com.juanimozo.matchpoint.presentation.match

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.juanimozo.matchpoint.navigation.Screens
import com.juanimozo.matchpoint.presentation.match.components.formatElapsedTime
import com.juanimozo.matchpoint.ui.theme.NavyBlue
import com.juanimozo.matchpoint.presentation.match.components.GenericButton
import com.juanimozo.matchpoint.ui.util.Set

@Composable
fun ResultScreen(navController: NavController, viewModel: MatchViewModel) {

    val matchState = viewModel.currentMatchState.value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        // Date
        Text(
            text = viewModel.currentMatchState.value.match.date,
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
            // Team 1
            Column(
                modifier = Modifier.fillMaxWidth(0.5f).padding(end = 8.dp),
                    verticalArrangement = Arrangement.SpaceAround) {
                Text(
                    text = matchState.match.team1.player1.name,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis
                )
                if (matchState.match.team1.player2 != null) {
                    Text(
                        text = matchState.match.team1.player2.name,
                        style = MaterialTheme.typography.subtitle1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            // Team 2
            Column(
                modifier = Modifier.fillMaxWidth().padding(end = 8.dp),
                verticalArrangement = Arrangement.SpaceAround) {
                Text(
                    text = matchState.match.team2.player1.name,
                    style = MaterialTheme.typography.subtitle1,
                    overflow = TextOverflow.Ellipsis
                )
                if (matchState.match.team2.player2 != null) {
                    Text(
                        text = matchState.match.team2.player2.name,
                        style = MaterialTheme.typography.subtitle1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        // First Set
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Set(
                title = "Primer Set",
                isCurrentSet = false,
                team1Games = matchState.match.set1Team1,
                team2Games = matchState.match.set1Team2
            )
            Set(
                title = "Segundo Set",
                isCurrentSet = false,
                team1Games = matchState.match.set2Team1 ?: 0,
                team2Games = matchState.match.set2Team2 ?: 0
            )
            Set(
                title = "Tercer Set",
                isCurrentSet = false,
                team1Games = matchState.match.set3Team1 ?: 0,
                team2Games = matchState.match.set3Team2 ?: 0
            )
        }

        if (viewModel.newMatchState.value.courtName.isNotBlank()) {
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
                    text = viewModel.newMatchState.value.courtName,
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
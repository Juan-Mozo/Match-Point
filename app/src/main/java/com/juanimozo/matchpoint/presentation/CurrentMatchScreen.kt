package com.juanimozo.matchpoint.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.juanimozo.matchpoint.MainActivityViewModel
import com.juanimozo.matchpoint.navigation.Screens
import com.juanimozo.matchpoint.presentation.components.Chronometer
import com.juanimozo.matchpoint.ui.theme.NavyBlue
import com.juanimozo.matchpoint.presentation.components.GenericButton
import com.juanimozo.matchpoint.ui.util.Set
import com.juanimozo.matchpoint.presentation.components.TeamPointsCard
import com.juanimozo.matchpoint.util.Sets
import com.juanimozo.matchpoint.util.Teams
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CurrentMatchScreen(navController: NavController, viewModel: MainActivityViewModel) {

    val match = viewModel.currentMatchState.value

    LaunchedEffect(Unit) {
        viewModel.userEventsState.collectLatest {
            if (it.isMatchEnded) {
                navController.navigate(Screens.Result.route)
            }
        }
    }

    // Chronometer
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Chronometer(viewModel)
    }

    Column(
        modifier = Modifier
            .padding(top = 45.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val isFirstSetCurrentSet = match.currentSet == Sets.FirstSet
            val isSecondSetCurrentSet = match.currentSet == Sets.SecondSet
            val isThirdSetCurrentSet = match.currentSet == Sets.ThirdSet
            // First Set
            Set(
                title = "Primer Set",
                isCurrentSet = isFirstSetCurrentSet,
                team1Games = match.team1GamesFirstSet.toString(),
                team2Games = match.team2GamesFirstSet.toString(),
                team1CurrentSetGames = match.currentSetTeam1.toString(),
                team2CurrentSetGames = match.currentSetTeam2.toString(),
                setWinnerTeam = match.winnerFirstSet
            )
            // Second Set
            Set(
                title = "Segundo Set",
                isCurrentSet = isSecondSetCurrentSet,
                team1Games = match.team1GamesSecondSet.toString(),
                team2Games = match.team2GamesSecondSet.toString(),
                team1CurrentSetGames = match.currentSetTeam1.toString(),
                team2CurrentSetGames = match.currentSetTeam2.toString(),
                setWinnerTeam = match.winnerSecondSet
            )
            // Third Set
            Set(
                title = "Tercer Set",
                isCurrentSet = isThirdSetCurrentSet,
                team1Games = match.team1GamesThirdSet.toString(),
                team2Games = match.team2GamesThirdSet.toString(),
                team1CurrentSetGames = match.currentSetTeam1.toString(),
                team2CurrentSetGames = match.currentSetTeam2.toString(),
                setWinnerTeam = match.winnerThirdSet
            )
        }

        // Current Points
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth(0.5f)) {

                // Team 1
                val team1points: String = if (viewModel.fieldState.value.countPoints) {
                    match.team1Points.s
                } else {
                    match.currentSetTeam1.toString()
                }
                TeamPointsCard(
                    points = team1points,
                    cardAction = { viewModel.sumPoint(Teams.Team1()) },
                    buttonAction = { viewModel.restPoint(Teams.Team1()) }
                )
            }

            // Team 2
            val team2points: String = if (viewModel.fieldState.value.countPoints) {
                match.team2Points.s
            } else {
                match.currentSetTeam2.toString()
            }
            TeamPointsCard(
                points = team2points,
                cardAction = { viewModel.sumPoint(Teams.Team2()) },
                buttonAction = { viewModel.restPoint(Teams.Team2()) }
            )
        }

        // Team names
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(0.33f),
                text = viewModel.fieldState.value.team1Name,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = "-",
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = viewModel.fieldState.value.team2Name,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Finish Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            GenericButton(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                color = NavyBlue,
                onClick = {
                    viewModel.endMatch()
                },
                text = "Terminar"
            )
        }
    }
}
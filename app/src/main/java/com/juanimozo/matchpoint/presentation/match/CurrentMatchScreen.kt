package com.juanimozo.matchpoint.presentation.match

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.juanimozo.matchpoint.navigation.Screens
import com.juanimozo.matchpoint.presentation.match.components.Chronometer
import com.juanimozo.matchpoint.ui.theme.NavyBlue
import com.juanimozo.matchpoint.presentation.components.GenericButton
import com.juanimozo.matchpoint.ui.util.Set
import com.juanimozo.matchpoint.presentation.match.components.TeamPointsCard
import com.juanimozo.matchpoint.util.Sets
import com.juanimozo.matchpoint.util.Teams
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CurrentMatchScreen(navController: NavController, viewModel: MatchViewModel) {

    val matchState = viewModel.currentMatchState.value
    val scaffoldState = rememberScaffoldState()
    var backPressedCount by remember { mutableStateOf(0)}

    LaunchedEffect(Unit) {
        viewModel.userEventsState.collectLatest {
            // Navigate to next screen when match has ended
            if (it.isMatchEnded) {
                navController.navigate(
                    Screens.Result.route.replace(
                        oldValue = "newMatch",
                        newValue = "true"
                    )
                )
            }
            // Show snackbar to warn user of closing screen
            if (it.snackbarMessage.isNotBlank()) {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = it.snackbarMessage,
                    duration = SnackbarDuration.Short,
                    actionLabel = "Volver"
                )
                when (snackbarResult) {
                    SnackbarResult.Dismissed -> {
                        backPressedCount = 0
                    }
                    SnackbarResult.ActionPerformed -> {
                        navController.popBackStack()
                    }
                }
            }
        }
    }

    BackHandler(enabled = true) {
        if (backPressedCount == 0) {
            viewModel.showSnackbar("¿Está seguro que desea volver? Se perderá la partida actual")
            backPressedCount++
        } else {
            navController.popBackStack()
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
            val isFirstSetCurrentSet = matchState.currentSet == Sets.FirstSet
            val isSecondSetCurrentSet = matchState.currentSet == Sets.SecondSet
            val isThirdSetCurrentSet = matchState.currentSet == Sets.ThirdSet
            // First Set
            Set(
                title = "Primer Set",
                isCurrentSet = isFirstSetCurrentSet,
                team1Games = matchState.match.set1Team1,
                team2Games = matchState.match.set1Team2,
                team1CurrentSetGames = matchState.currentSetTeam1,
                team2CurrentSetGames = matchState.currentSetTeam2
            )
            // Second Set
            Set(
                title = "Segundo Set",
                isCurrentSet = isSecondSetCurrentSet,
                team1Games = matchState.match.set2Team1,
                team2Games = matchState.match.set2Team2,
                team1CurrentSetGames = matchState.currentSetTeam1,
                team2CurrentSetGames = matchState.currentSetTeam2
            )
            // Third Set
            Set(
                title = "Tercer Set",
                isCurrentSet = isThirdSetCurrentSet,
                team1Games = matchState.match.set3Team1,
                team2Games = matchState.match.set3Team2,
                team1CurrentSetGames = matchState.currentSetTeam1,
                team2CurrentSetGames = matchState.currentSetTeam2
            )
        }

        // Current Points
        Row(modifier = Modifier.fillMaxWidth()) {
            // Team 1
            Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                val team1points: String = if (viewModel.newMatchState.value.countPoints) {
                    matchState.team1Points.s
                } else {
                    matchState.currentSetTeam1.toString()
                }
                TeamPointsCard(
                    points = team1points,
                    cardAction = { viewModel.sumPoint(Teams.Team1()) },
                    buttonAction = { viewModel.restPoint(Teams.Team1()) }
                )
            }
            // Team 2
            val team2points: String = if (viewModel.newMatchState.value.countPoints) {
                matchState.team2Points.s
            } else {
                matchState.currentSetTeam2.toString()
            }
            TeamPointsCard(
                points = team2points,
                cardAction = { viewModel.sumPoint(Teams.Team2()) },
                buttonAction = { viewModel.restPoint(Teams.Team2()) }
            )
        }

        // Team names
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(end = 8.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PlayerName(matchState.match.team1.player1.name)
                if (matchState.match.team1.player2 != null) {
                    PlayerName(matchState.match.team1.player2.name)
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PlayerName(matchState.match.team2.player1.name)
                if (matchState.match.team2.player2 != null) {
                    PlayerName(matchState.match.team2.player2.name)
                }
            }
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

@Composable
private fun PlayerName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.subtitle1,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}
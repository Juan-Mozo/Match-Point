package com.juanimozo.matchpoint.presentation.match

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.juanimozo.matchpoint.R
import com.juanimozo.matchpoint.domain.model.MatchWithTeamsModel
import com.juanimozo.matchpoint.domain.model.PlayerModel
import com.juanimozo.matchpoint.domain.model.TeamModel
import com.juanimozo.matchpoint.navigation.Screens
import com.juanimozo.matchpoint.presentation.components.CustomTopBar
import com.juanimozo.matchpoint.presentation.match.components.formatElapsedTime
import com.juanimozo.matchpoint.ui.theme.NavyBlue
import com.juanimozo.matchpoint.presentation.components.GenericButton
import com.juanimozo.matchpoint.ui.util.Set

@Composable
fun ResultScreen(navController: NavController, viewModel: MatchViewModel) {

    val match = viewModel.currentMatchState.value.match

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        BackgroundImageAndTeamNames(match)

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // Date
            Text(
                text = match.simplifiedDate,
                style = MaterialTheme.typography.subtitle2
            )
            // Teams and results
            Column(
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Set(
                        title = "Primer Set",
                        isCurrentSet = false,
                        team1Games = match.set1Team1,
                        team2Games = match.set1Team2
                    )
                    Set(
                        title = "Segundo Set",
                        isCurrentSet = false,
                        team1Games = match.set2Team1,
                        team2Games = match.set2Team2
                    )
                    Set(
                        title = "Tercer Set",
                        isCurrentSet = false,
                        team1Games = match.set3Team1,
                        team2Games = match.set3Team2
                    )
                }
            }

            if (match.courtName.isNotBlank()) {
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
                        text = match.courtName,
                        style = MaterialTheme.typography.subtitle2,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }

            // Duration
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Duración: ${formatElapsedTime(match.duration, simplified = true)}",
                    style = MaterialTheme.typography.body1
                )
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
                    onClick = { navController.navigate(Screens.History.route) },
                    text = "Volver"
                )
            }
        }
    }
}

@Composable
private fun BackgroundImageAndTeamNames(match: MatchWithTeamsModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.33f)
            .padding(bottom = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp)
                .clip(RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp))
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.background_3),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 32.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = 8.dp
        ) {
            // Team Names
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Team 1
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.33f),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PlayerText(match.team1.player1.name)
                    if (match.team1.player2 != null) {
                        PlayerText(match.team1.player2.name)
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        text = "-",
                        style = MaterialTheme.typography.subtitle2.copy(
                            color = NavyBlue
                        )
                    )
                }
                // Team 2
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PlayerText(match.team2.player1.name)
                    if (match.team2.player2 != null) {
                        PlayerText(match.team2.player2.name)
                    }
                }
            }
        }
    }
}

@Composable
private fun PlayerText(name: String) {
    Text(
        modifier = Modifier.padding(8.dp),
        text = name,
        style = MaterialTheme.typography.subtitle1.copy(
            fontSize = 22.sp
        ),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}
package com.juanimozo.matchpoint.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juanimozo.matchpoint.data.database.entity.Match
import com.juanimozo.matchpoint.ui.theme.Beige
import com.juanimozo.matchpoint.ui.theme.LightGreen
import com.juanimozo.matchpoint.ui.theme.Shapes
import com.juanimozo.matchpoint.util.Teams

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryMatch(match: Match, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Beige,
        shape = Shapes.medium,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Team 1
            Row(
                modifier = Modifier.padding(horizontal = 4.dp)
                    .fillMaxWidth(0.33f),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = match.team1Name,
                    style = MaterialTheme.typography.body1,
                    color = Teams.getTeamColor(team = Teams.Team1(), winnerTeam = Teams.convertIntToTeam(match.winnerTeam))
                )
            }
            // Sets
            Column(
                modifier = Modifier
                    .padding(vertical = 6.dp, horizontal = 4.dp)
                    .fillMaxWidth(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // First Set
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Text(
                        text = match.set1Team1.toString(),
                        style = MaterialTheme.typography.body2,
                        color = Teams.getTeamColor(team = Teams.Team1(), winnerTeam = Teams.convertIntToTeam(match.winnerFirstSet))
                    )
                    Text(
                        text = "-",
                        style = MaterialTheme.typography.body2
                    )
                    Text(
                        text = match.set1Team2.toString(),
                        style = MaterialTheme.typography.body2,
                        color = Teams.getTeamColor(team = Teams.Team2(), winnerTeam = Teams.convertIntToTeam(match.winnerFirstSet))
                    )
                }
                // Second Set
                if (match.set2Team1 != 0) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        Text(
                            text = match.set2Team1.toString(),
                            style = MaterialTheme.typography.body2,
                            color = Teams.getTeamColor(team = Teams.Team1(), winnerTeam = Teams.convertIntToTeam(match.winnerSecondSet))
                        )
                        Text(
                            text = "-",
                            style = MaterialTheme.typography.body2
                        )
                        Text(
                            text = match.set2Team2.toString(),
                            style = MaterialTheme.typography.body2,
                            color = Teams.getTeamColor(team = Teams.Team2(), winnerTeam = Teams.convertIntToTeam(match.winnerSecondSet))
                        )
                    }
                }
                // Third Set
                if (match.set3Team1 != 0) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        Text(
                            text = match.set3Team1.toString(),
                            style = MaterialTheme.typography.body2,
                            color = Teams.getTeamColor(team = Teams.Team1(), winnerTeam = Teams.convertIntToTeam(match.winnerThirdSet))
                        )
                        Text(
                            text = "-",
                            style = MaterialTheme.typography.body2
                        )
                        Text(
                            text = match.set3Team2.toString(),
                            style = MaterialTheme.typography.body2,
                            color = Teams.getTeamColor(team = Teams.Team2(), winnerTeam = Teams.convertIntToTeam(match.winnerThirdSet))
                        )
                    }
                }
            }
            // Team 2
            Text(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth(),
                text = match.team2Name,
                style = MaterialTheme.typography.body1,
                color = Teams.getTeamColor(team = Teams.Team2(), winnerTeam = Teams.convertIntToTeam(match.winnerTeam))
            )
        }
    }
}
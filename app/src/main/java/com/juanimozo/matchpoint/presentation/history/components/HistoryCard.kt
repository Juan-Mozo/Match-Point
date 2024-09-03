package com.juanimozo.matchpoint.presentation.history.components

import android.annotation.SuppressLint
import android.view.FrameMetrics
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.juanimozo.matchpoint.data.database.entity.Match
import com.juanimozo.matchpoint.ui.theme.BackgroundWhite
import com.juanimozo.matchpoint.ui.theme.LightNavyBlue
import com.juanimozo.matchpoint.ui.theme.Shapes
import com.juanimozo.matchpoint.util.Teams
import kotlin.math.roundToInt

@SuppressLint("UnusedTransitionTargetStateParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryCard(
    match: Match,
    isRevealed: Boolean = true,
    cardOffset: Float,
    cardHeight: Dp,
    onClick: () -> Unit,
    onCollapse: () -> Unit,
    onExpand: () -> Unit
) {

    val offsetX by remember { mutableStateOf(0f) }
    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }
    val transition = updateTransition(transitionState, label = "")
    val offsetTransition by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = FrameMetrics.ANIMATION_DURATION) },
        targetValueByState = {
            if (isRevealed) cardOffset - offsetX else -offsetX
        }
    )

    Card(
        modifier = Modifier
            .height(cardHeight)
            .fillMaxWidth()
            .offset { IntOffset((offsetX + offsetTransition).roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    val original = Offset(offsetX, 0f)
                    val summed = original + Offset(x = dragAmount, y = 0f)
                    val newValue = Offset(x = summed.x.coerceIn(0f, cardOffset), y = 0f)
                    if (newValue.x >= 10) {
                        onExpand()
                        return@detectHorizontalDragGestures
                    } else if (newValue.x <= 0) {
                        onCollapse()
                        return@detectHorizontalDragGestures
                    }
                }
            },
        backgroundColor = BackgroundWhite,
        border = BorderStroke(0.25.dp, LightNavyBlue),
        shape = Shapes.medium,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Date and Court
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(0.33f)
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    text = match.simplifiedDate,
                    style = MaterialTheme.typography.body2
                )
                if (match.courtName.isNotBlank()) {
                    Text(
                        text = "cancha: ${match.courtName}",
                        style = MaterialTheme.typography.body2,
                        maxLines = 1
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Teams
                Column(
                    modifier = Modifier
                        .padding()
                        .fillMaxHeight()
                        .fillMaxWidth(0.55f),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Team 1 name
                    Text(
                        text = match.team1Name,
                        style = MaterialTheme.typography.body1,
                        color = Teams.getTeamColor(team = Teams.Team1(), winnerTeam = Teams.convertIntToTeam(match.winnerTeam)),
                        maxLines = 1
                    )
                    // Team 2 name
                    Text(
                        text = match.team2Name,
                        style = MaterialTheme.typography.body1,
                        color = Teams.getTeamColor(team = Teams.Team2(), winnerTeam = Teams.convertIntToTeam(match.winnerTeam)),
                        maxLines = 1
                    )
                }
                // Result
                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // First set
                    Set(
                        team1Result = match.set1Team1.toString(),
                        team2Result = match.set1Team2.toString(),
                        winnerTeamInt = match.winnerFirstSet
                    )
                    // Second Set
                    Set(
                        team1Result = match.set2Team1.toString(),
                        team2Result = match.set2Team2.toString(),
                        winnerTeamInt = match.winnerSecondSet
                    )
                    // Third Set
                    Set(
                        team1Result = match.set3Team1.toString(),
                        team2Result = match.set3Team2.toString(),
                        winnerTeamInt = match.winnerThirdSet
                    )
                }
            }
        }
    }
}

@Composable
private fun Set(team1Result: String, team2Result: String, winnerTeamInt: Int) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val team1ResultColor = if (team1Result == "0") { Gray } else { Teams.getTeamColor(team = Teams.Team1(), winnerTeam = Teams.convertIntToTeam(winnerTeamInt)) }
        val team2ResultColor = if (team2Result == "0") { Gray } else { Teams.getTeamColor(team = Teams.Team2(), winnerTeam = Teams.convertIntToTeam(winnerTeamInt)) }

        Text(
            text = team1Result,
            style = MaterialTheme.typography.body1,
            color = team1ResultColor
        )
        Text(
            text = team2Result,
            style = MaterialTheme.typography.body1,
            color = team2ResultColor
        )
    }
}

@Preview
@Composable
fun HistoryCardPreview() {
    HistoryCard(
        match = Match(
            team1Name = "TEAM 1",
            team2Name = "TEAM 2"
        ),
        cardOffset = 90.dp.value,
        cardHeight = 100.dp,
        onClick = {  },
        onCollapse = {  }) {
    }
}
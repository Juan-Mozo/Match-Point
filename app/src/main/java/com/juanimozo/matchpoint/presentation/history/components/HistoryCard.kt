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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.juanimozo.matchpoint.domain.model.MatchWithTeamsModel
import com.juanimozo.matchpoint.presentation.components.PlayerNameText
import com.juanimozo.matchpoint.ui.theme.BackgroundWhite
import com.juanimozo.matchpoint.ui.theme.LightNavyBlue
import com.juanimozo.matchpoint.ui.theme.Shapes
import kotlin.math.roundToInt

@SuppressLint("UnusedTransitionTargetStateParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HistoryCard(
    match: MatchWithTeamsModel,
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
                    verticalArrangement = Arrangement.Top
                ) {
                    val isDoubles = match.team1.player2 != null
                    // Team 1
                    // Player 1
                    PlayerNameText(
                        name = match.team1.player1.name,
                        isWinner = false
                    )
                    if (isDoubles) {
                        // Player 3
                        PlayerNameText(
                            paddingTop = 4.dp,
                            name = match.team1.player2!!.name,
                            isWinner = false
                        )
                    }

                    // Team 2
                    // Player 2
                    PlayerNameText(
                        paddingTop = 8.dp,
                        name = match.team2.player1.name,
                        isWinner = false
                    )
                    if (isDoubles) {
                        // Player 4
                        PlayerNameText(
                            paddingTop = 4.dp,
                            name = match.team2.player2!!.name,
                            isWinner = false
                        )
                    }
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
                        team1Result = match.set1Team1,
                        team2Result = match.set1Team2
                    )
                    // Second Set
                    Set(
                        team1Result = match.set2Team1 ?: 0,
                        team2Result = match.set2Team2 ?: 0
                    )
                    // Third Set
                    Set(
                        team1Result = match.set3Team1 ?: 0,
                        team2Result = match.set3Team2 ?: 0
                    )
                }
            }
        }
    }
}

@Composable
private fun Set(team1Result: Int, team2Result: Int) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val isWinnerTeam1 = if (team1Result > team2Result) { MaterialTheme.typography.body2 } else { MaterialTheme.typography.body1 }
        val isWinnerTeam2 = if (team1Result < team2Result) { MaterialTheme.typography.body2 } else { MaterialTheme.typography.body1 }

        Text(
            text = team1Result.toString(),
            style = isWinnerTeam1
        )
        Text(
            text = team2Result.toString(),
            style = isWinnerTeam2
        )
    }
}
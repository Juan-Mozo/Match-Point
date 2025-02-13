package com.juanimozo.matchpoint.presentation.history.components

import android.annotation.SuppressLint
import android.view.FrameMetrics
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juanimozo.matchpoint.R
import com.juanimozo.matchpoint.domain.model.MatchWithTeamsModel
import com.juanimozo.matchpoint.domain.model.TeamModel
import com.juanimozo.matchpoint.presentation.match.components.formatElapsedTime
import com.juanimozo.matchpoint.ui.theme.BackgroundWhite
import com.juanimozo.matchpoint.ui.theme.DarkGreen
import com.juanimozo.matchpoint.ui.theme.LightNavyBlue
import com.juanimozo.matchpoint.ui.theme.Shapes
import com.juanimozo.matchpoint.ui.theme.Size
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
    val transition = rememberTransition(transitionState, label = "")
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
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // Date
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = match.date,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = DarkGreen
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
            // Court and Duration
            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                // Court
                Column(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = match.courtName,
                        style = MaterialTheme.typography.body1.copy(
                            fontSize = 18.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                // Duration
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = formatElapsedTime(match.duration, simplified = true),
                        style = MaterialTheme.typography.body1.copy(
                            fontSize = 18.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Thin
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
            }
            // Players and Results
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                val isTeam1Winner = match.winnerTeam == 1
                val isTeam2Winner = match.winnerTeam == 2

                Team(
                    team = match.team1,
                    resultFirstSet = match.set1Team1,
                    resultSecondSet = match.set2Team1,
                    resultThirdSet = match.set3Team1,
                    isWinner = isTeam1Winner
                )
                Divider(
                    modifier = Modifier
                        .padding(horizontal = 18.dp)
                        .fillMaxWidth()
                )
                Team(
                    team = match.team2,
                    resultFirstSet = match.set1Team2,
                    resultSecondSet = match.set2Team2,
                    resultThirdSet = match.set3Team2,
                    isWinner = isTeam2Winner
                )
            }
        }
    }
}

@Composable
private fun Team(
    team: TeamModel,
    resultFirstSet: Int,
    resultSecondSet: Int,
    resultThirdSet: Int,
    isWinner: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.4f)
        ) {
            Player(
                name = team.player1.name,
                isWinner = isWinner
            )
            if (team.player2 != null) {
                Player(
                    name = team.player2.name,
                    isWinner = isWinner
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth(0.25f)
        ) {
            if (isWinner) {
                Icon(
                    painterResource(R.drawable.ic_check),
                    modifier = Modifier.size(Size.Icon.medium),
                    contentDescription = "Winner team"
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Set(0.333f, resultFirstSet, isWinner)
            Set(0.5f, resultSecondSet, isWinner)
            Set(1f, resultThirdSet, isWinner)
        }
    }
}

@Composable
private fun Player(name: String, isWinner: Boolean) {
    val color = if (isWinner) { DarkGreen } else { Color.Gray }
    Text(
        text = name,
        style = MaterialTheme.typography.body1.copy(
            color = color,
            fontWeight = FontWeight.Bold
        ),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Composable
private fun Set(widthFraction: Float, result: Int, isWinner: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(widthFraction),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val color = if (isWinner) { DarkGreen } else { Color.Gray }
        Text(
            text = result.toString(),
            style = MaterialTheme.typography.body1.copy(
                color = color,
                fontWeight = FontWeight.Bold
            ),
            overflow = TextOverflow.Ellipsis
        )
    }
}
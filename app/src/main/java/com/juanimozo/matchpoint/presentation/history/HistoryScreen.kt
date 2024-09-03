package com.juanimozo.matchpoint.presentation.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.W
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.juanimozo.matchpoint.R
import com.juanimozo.matchpoint.navigation.Screens
import com.juanimozo.matchpoint.presentation.history.components.ActionsRow
import com.juanimozo.matchpoint.ui.theme.NavyBlue
import com.juanimozo.matchpoint.presentation.match.components.GenericButton
import com.juanimozo.matchpoint.presentation.history.components.HistoryCard
import com.juanimozo.matchpoint.presentation.history.event.HistoryEvents
import com.juanimozo.matchpoint.presentation.match.MatchViewModel

@Composable
fun HistoryScreen(navController: NavController, historyViewModel: HistoryViewModel, matchViewModel: MatchViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        if (historyViewModel.pastResultsState.value.matches.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.background_2),
                    contentDescription = "A tennis ball on a tennis court",
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    // Title
                    Card(
                        modifier = Modifier.padding(top = 8.dp),
                        backgroundColor =  Color.White
                    ) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = "HISTORIAL",
                            style = MaterialTheme.typography.h2
                        )
                    }
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .fillMaxHeight(0.75f),
                        backgroundColor = Color.White,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    ) {
                        LazyColumn {
                            items(historyViewModel.pastResultsState.value.matches) { match ->
                                Box(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {

                                    val cardHeight = 100.dp
                                    val actionRowWidth = 100.dp

                                    ActionsRow(
                                        height = cardHeight,
                                        width = actionRowWidth,
                                        onDelete = {
                                            historyViewModel.deleteMatch(match)
                                        }
                                    )
                                    HistoryCard(
                                        match = match,
                                        isRevealed = historyViewModel.pastResultsState.value.revealedCard == match,
                                        cardOffset = actionRowWidth.value - 10.dp.value,
                                        cardHeight = cardHeight,
                                        onClick = {
                                            matchViewModel.updateCurrentMatchState(match)
                                            navController.navigate(Screens.Result.route)
                                        },
                                        onCollapse = { historyViewModel.onHistoryEvent(HistoryEvents.CollapseCard(match)) },
                                        onExpand = { historyViewModel.onHistoryEvent(HistoryEvents.ExpandCard(match)) }
                                    )
                                }
                                Divider(
                                    modifier = Modifier
                                        .fillMaxWidth(0.60f)
                                        .padding(vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxHeight(0.75f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "El historial está vacío",
                    style = MaterialTheme.typography.h3,
                    textAlign = TextAlign.Center
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
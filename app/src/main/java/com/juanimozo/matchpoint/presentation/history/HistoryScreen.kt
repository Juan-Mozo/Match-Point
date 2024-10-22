package com.juanimozo.matchpoint.presentation.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.juanimozo.matchpoint.R
import com.juanimozo.matchpoint.domain.model.PlayerModel
import com.juanimozo.matchpoint.navigation.Screens
import com.juanimozo.matchpoint.presentation.components.DynamicSelectTextField
import com.juanimozo.matchpoint.presentation.history.components.ActionsRow
import com.juanimozo.matchpoint.ui.theme.NavyBlue
import com.juanimozo.matchpoint.presentation.components.GenericButton
import com.juanimozo.matchpoint.presentation.components.animation.VisibilityAnimation
import com.juanimozo.matchpoint.presentation.history.components.HistoryCard
import com.juanimozo.matchpoint.presentation.history.event.HistoryEvents
import com.juanimozo.matchpoint.presentation.history.state.HistoryState
import com.juanimozo.matchpoint.presentation.match.MatchViewModel
import com.juanimozo.matchpoint.ui.theme.BackgroundWhite
import com.juanimozo.matchpoint.ui.theme.DarkGreen

@Composable
fun HistoryScreen(navController: NavController, historyViewModel: HistoryViewModel, matchViewModel: MatchViewModel) {
    val state = historyViewModel.historyState.value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        if (state.matches.isNotEmpty()) {
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
                    verticalArrangement = Arrangement.Top
                ) {
                    HistoryScreenTitle(
                        state = state,
                        onTextValueChange = { query ->
                            historyViewModel.onHistoryEvent(HistoryEvents.UpdatePlayerName(query))
                        },
                        onValueChangedEvent = { player ->
                            historyViewModel.onHistoryEvent(HistoryEvents.SelectPlayer(player))
                        }
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(historyViewModel.historyState.value.matches) { match ->
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                val cardHeight = 200.dp
                                val actionRowWidth = 200.dp

                                ActionsRow(
                                    height = cardHeight,
                                    width = actionRowWidth,
                                    onDelete = {
                                        historyViewModel.deleteMatch(match)
                                    }
                                )
                                HistoryCard(
                                    match = match,
                                    isRevealed = historyViewModel.historyState.value.revealedCard == match,
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

        if (state.matches.isEmpty()) {
            NoMatchFoundScreen()
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

@Composable
private fun NoMatchFoundScreen() {
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

@Composable
private fun HistoryScreenTitle(
    state: HistoryState,
    onTextValueChange: (String) -> Unit,
    onValueChangedEvent: (PlayerModel) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
        backgroundColor =  BackgroundWhite
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "HISTORIAL",
                    style = MaterialTheme.typography.h2.copy(
                        fontSize = 30.sp
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 16.dp, start = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filtrar",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center
                )
                DynamicSelectTextField(
                    textFieldValue = state.playerTextField,
                    options = state.playersFound,
                    label = "Jugador",
                    onTextValueChange = { query -> onTextValueChange(query) },
                    showAddNewPlayer = false,
                    onValueChangedEvent = { player -> onValueChangedEvent(player)}
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewHistoryScreenTitle(){
    HistoryScreenTitle(
        state = HistoryState(),
        onValueChangedEvent = {},
        onTextValueChange = {}
    )
}
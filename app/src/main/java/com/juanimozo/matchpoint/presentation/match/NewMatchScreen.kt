package com.juanimozo.matchpoint.presentation.match

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.juanimozo.matchpoint.presentation.match.components.Field
import com.juanimozo.matchpoint.presentation.match.event.NewMatchEvents
import com.juanimozo.matchpoint.ui.theme.NavyBlue
import com.juanimozo.matchpoint.presentation.match.components.GenericButton
import com.juanimozo.matchpoint.presentation.match.components.MatchTypeCard
import com.juanimozo.matchpoint.presentation.match.components.NewPlayerPopUp
import com.juanimozo.matchpoint.presentation.match.components.field.DynamicSelectTextField
import com.juanimozo.matchpoint.ui.theme.LightNavyBlue
import com.juanimozo.matchpoint.util.Date
import com.juanimozo.matchpoint.util.MatchType
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NewMatchScreen(navController: NavController, viewModel: MatchViewModel) {

    val newMatchState = viewModel.newMatchState.value
    val scaffoldState = rememberScaffoldState()
    val isSinglesSelected = viewModel.newMatchState.value.matchType == MatchType.SINGLES
    val isDoublesSelected = viewModel.newMatchState.value.matchType == MatchType.DOUBLES

    LaunchedEffect(Unit) {
        viewModel.userEventsState.collectLatest {
            // Show Snackbar when any team name is blank
            if (it.isMessageShowed) {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Por favor completar los campos vacíos",
                    duration = SnackbarDuration.Short,
                    actionLabel = "Deshacer"
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    snackbarData = data,
                    backgroundColor = LightNavyBlue,
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                        MatchTypeCard(text = "Singles", isSinglesSelected) {
                            viewModel.onRegistrationEvent(NewMatchEvents.UpdateMatchType(MatchType.SINGLES))
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth()) {
                        MatchTypeCard(text = "Dobles", isDoublesSelected) {
                            viewModel.onRegistrationEvent(NewMatchEvents.UpdateMatchType(MatchType.DOUBLES))
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    FieldTitle(text = "Equipo 1")

                    DynamicSelectTextField(
                        selectedValue = newMatchState.player1.player,
                        textFieldValue = newMatchState.player1TextField,
                        options = newMatchState.playersFound,
                        label = "Jugador",
                        onTextValueChange = { query ->
                            viewModel.onRegistrationEvent(NewMatchEvents.UpdatePlayerName(1, query))
                        },
                        onValueChangedEvent = { player ->
                            viewModel.onRegistrationEvent(NewMatchEvents.SelectPlayer(1, player))
                        },
                        onAddNewPlayerEvent = {
                            viewModel.onRegistrationEvent(NewMatchEvents.ManageNewPlayerPopup(true, 0))
                        }
                    )

                    if (isDoublesSelected) {
                        DynamicSelectTextField(
                            selectedValue = newMatchState.player3.player,
                            textFieldValue = newMatchState.player3TextField,
                            options = newMatchState.playersFound,
                            label = "Jugador",
                            onTextValueChange = { query ->
                                viewModel.onRegistrationEvent(NewMatchEvents.UpdatePlayerName(3, query))
                            },
                            onValueChangedEvent = { player ->
                                viewModel.onRegistrationEvent(NewMatchEvents.SelectPlayer(3, player))
                            },
                            onAddNewPlayerEvent = {
                                viewModel.onRegistrationEvent(NewMatchEvents.ManageNewPlayerPopup(true, 3))
                            }
                        )
                    }

                    FieldTitle(text = "Equipo 2")

                    DynamicSelectTextField(
                        selectedValue = newMatchState.player2.player,
                        textFieldValue = newMatchState.player2TextField,
                        options = newMatchState.playersFound,
                        label = "Jugador",
                        onTextValueChange = { query ->
                            viewModel.onRegistrationEvent(NewMatchEvents.UpdatePlayerName(2, query))
                        },
                        onValueChangedEvent = { player ->
                            viewModel.onRegistrationEvent(NewMatchEvents.SelectPlayer(2, player))
                        },
                        onAddNewPlayerEvent = {
                            viewModel.onRegistrationEvent(NewMatchEvents.ManageNewPlayerPopup(true, 2))
                        }
                    )

                    if (isDoublesSelected) {
                        DynamicSelectTextField(
                            selectedValue = newMatchState.player4.player,
                            textFieldValue = newMatchState.player4TextField,
                            options = newMatchState.playersFound,
                            label = "Jugador",
                            onTextValueChange = { query ->
                                viewModel.onRegistrationEvent(NewMatchEvents.UpdatePlayerName(4, query))
                            },
                            onValueChangedEvent = { player ->
                                viewModel.onRegistrationEvent(NewMatchEvents.SelectPlayer(4, player))
                            },
                            onAddNewPlayerEvent = {
                                viewModel.onRegistrationEvent(NewMatchEvents.ManageNewPlayerPopup(true, 4))
                            }
                        )
                    }

                    FieldTitle(text = "Cancha")

                    // Court
                    OutlinedTextField(
                        value = newMatchState.courtName,
                        onValueChange = { query -> viewModel.onRegistrationEvent(NewMatchEvents.UpdateCourtName(query)) },
                        label = {
                            Text(
                                text = "Cancha",
                                style = MaterialTheme.typography.subtitle2
                            )
                        }
                    )
                }

                // Count Points
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Column {
                        Text(
                            text = "Contar puntos",
                            style = MaterialTheme.typography.subtitle1
                        )
                        Text(
                            text = "(Si elige esta opción se contará punto por punto, por el contrario se contaran " + "directamente los games)",
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center
                        )
                    }

                    if (newMatchState.countPoints) {
                        OutlinedButton(
                            onClick = { viewModel.onRegistrationEvent(NewMatchEvents.UpdateCountPoints(false)) }
                        ) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = "Check")
                        }
                    } else {
                        OutlinedButton(
                            onClick = { viewModel.onRegistrationEvent(NewMatchEvents.UpdateCountPoints(true))},
                            border = BorderStroke(3.dp, NavyBlue)
                        ) {}
                    }
                }

                // New Match Button
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    GenericButton(
                        modifier = Modifier
                            .padding(horizontal = 64.dp)
                            .fillMaxWidth(),
                        color = NavyBlue,
                        onClick = {
                            // Start Match
                            viewModel.startMatch(
                                currentTime = System.currentTimeMillis() - viewModel.chronometerState.value.elapsedTime,
                                currentDate = Date().getDate(),
                                simplifiedDate = Date().getSimplifiedDate(),
                                navController = navController
                            )
                        },
                        text = "Iniciar"
                    )
                }
            }

            NewPlayerPopUp(
                showPopUp = newMatchState.showPopup,
                value = newMatchState.newPlayerName,
                onClickOutside = { viewModel.onRegistrationEvent(NewMatchEvents.ManageNewPlayerPopup(false, 0))},
                onTextValueChange = { query -> viewModel.onRegistrationEvent(NewMatchEvents.UpdateNewPlayerName(query)) },
                onFinishButton = {
                    if (newMatchState.newPlayerName.isNotBlank()) {
                        viewModel.addNewPlayer()
                        viewModel.onRegistrationEvent(NewMatchEvents.ManageNewPlayerPopup(false, 0))
                    } else  {
                        viewModel.showSnackbar()
                    }
                }
            )
        }
    }
}

@Composable
private fun FieldTitle(text: String) {
    Text(
        modifier = Modifier.padding(vertical = 16.dp),
        text = text,
        style = MaterialTheme.typography.subtitle2
    )
}
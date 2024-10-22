package com.juanimozo.matchpoint.presentation.match

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.juanimozo.matchpoint.R
import com.juanimozo.matchpoint.presentation.match.event.NewMatchEvents
import com.juanimozo.matchpoint.ui.theme.NavyBlue
import com.juanimozo.matchpoint.presentation.components.GenericButton
import com.juanimozo.matchpoint.presentation.match.components.MatchTypeCard
import com.juanimozo.matchpoint.presentation.match.components.NewPlayerPopUp
import com.juanimozo.matchpoint.presentation.components.DynamicSelectTextField
import com.juanimozo.matchpoint.presentation.components.animation.AnimationType
import com.juanimozo.matchpoint.presentation.components.animation.VisibilityAnimation
import com.juanimozo.matchpoint.ui.theme.DarkGreen
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
        // Observe snackbar state
        viewModel.userEventsState.collectLatest {
            if (it.snackbarMessage.isNotBlank()) {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = it.snackbarMessage,
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
                    backgroundColor = Color.White,
                    actionColor = DarkGreen,
                    snackbarData = data
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().height(60.dp)
                ) {
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
                Row(modifier = Modifier.fillMaxSize()) {
                    AnimatedContent(
                        targetState = newMatchState.matchType,
                        label = "NewMatchFormulary Animation"
                    ) { targetState ->
                        if (targetState == MatchType.SINGLES) {
                            NewMatchFormulary(navController, viewModel, MatchType.SINGLES)
                        } else {
                            NewMatchFormulary(navController, viewModel, MatchType.DOUBLES)
                        }
                    }
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
                        viewModel.showSnackbar("Por favor completar el campo vacío")
                    }
                }
            )
        }
    }
}

@Composable
private fun FieldTitle(text: String) {
    Text(
        modifier = Modifier.padding(vertical = 12.dp),
        text = text,
        style = MaterialTheme.typography.subtitle2
    )
}

@Composable
private fun NewMatchFormulary(navController: NavController, viewModel: MatchViewModel, matchType: MatchType) {
    val newMatchState = viewModel.newMatchState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        FieldTitle(text = "Equipo 1")

        DynamicSelectTextField(
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

        if (matchType == MatchType.DOUBLES) {
            DynamicSelectTextField(
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

        if (matchType == MatchType.DOUBLES) {
            DynamicSelectTextField(
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

        // Count Points
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Contar puntos",
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = "(Si elige esta opción se contará punto por punto, por el contrario se contaran " + "directamente los games)",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Justify
                )
            }

            OutlinedButton(
                onClick = { viewModel.onRegistrationEvent(NewMatchEvents.UpdateCountPoints(false)) },
                border = BorderStroke(3.dp, NavyBlue)
            ) {
                VisibilityAnimation(newMatchState.countPoints, AnimationType.FadeAnimation(100)) {
                    Icon(painterResource(R.drawable.ic_check), contentDescription = "Check")
                }
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
}
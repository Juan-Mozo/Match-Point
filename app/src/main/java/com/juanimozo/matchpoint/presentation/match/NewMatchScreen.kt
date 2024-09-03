package com.juanimozo.matchpoint.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
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
import com.juanimozo.matchpoint.presentation.match.MatchViewModel
import com.juanimozo.matchpoint.presentation.match.components.Field
import com.juanimozo.matchpoint.presentation.match.event.NewMatchEvents
import com.juanimozo.matchpoint.ui.theme.NavyBlue
import com.juanimozo.matchpoint.presentation.match.components.GenericButton
import com.juanimozo.matchpoint.ui.theme.LightNavyBlue
import com.juanimozo.matchpoint.util.Date
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NewMatchScreen(navController: NavController, viewModel: MatchViewModel) {

    val fieldState = viewModel.fieldState.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(Unit) {
        // Show Snackbar when any team name is blank
        viewModel.userEventsState.collectLatest {
            if (it.isMessageShowed) {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = "Por favor ponerle nombre a los equipos",
                    duration = SnackbarDuration.Short,
                    actionLabel = "Deshacer"
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 45.dp),
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
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Field(
                    title = "Equipo 1",
                    label = "Nombre...",
                    value = fieldState.team1Name,
                    onValueChange = { query -> viewModel.onRegistrationEvent(NewMatchEvents.UpdateTeam1Name(query)) }
                )
                Field(
                    title = "Equipo 2",
                    label = "Nombre...",
                    value = fieldState.team2Name,
                    onValueChange = { query -> viewModel.onRegistrationEvent(NewMatchEvents.UpdateTeam2Name(query)) }
                )
                Field(
                    title = "Cancha",
                    label = "Nombre...",
                    value = fieldState.courtName,
                    onValueChange = { query -> viewModel.onRegistrationEvent(NewMatchEvents.UpdateCourtName(query)) }
                )
            }

            // Count Points
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Contar puntos",
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = "(Si elige esta opción se contará punto por punto, por el contrario se contaran " + "directamente los games)",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center)
            }
            if (fieldState.countPoints) {
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
}
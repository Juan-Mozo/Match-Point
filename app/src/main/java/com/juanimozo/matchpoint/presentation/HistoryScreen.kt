package com.juanimozo.matchpoint.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.juanimozo.matchpoint.MainActivityViewModel
import com.juanimozo.matchpoint.navigation.Screens
import com.juanimozo.matchpoint.ui.theme.NavyBlue
import com.juanimozo.matchpoint.presentation.components.GenericButton
import com.juanimozo.matchpoint.presentation.components.HistoryMatch

@Composable
fun HistoryScreen(navController: NavController, viewModel: MainActivityViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        // Title
        Text(
            text = "Historial",
            style = MaterialTheme.typography.h2
        )
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxHeight(0.75f)
        ) {
            items(viewModel.pastResultsState.value.matches) { match ->
                HistoryMatch(
                    match = match,
                    onClick = {
                        viewModel.updateCurrentMatchState(match)
                        navController.navigate(Screens.Result.route)
                    }
                )
                Divider(
                    modifier = Modifier.padding(vertical = 4.dp)
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
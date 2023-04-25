package com.juanimozo.matchpoint.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.juanimozo.matchpoint.MainActivityViewModel
import com.juanimozo.matchpoint.navigation.Screens
import com.juanimozo.matchpoint.ui.theme.Green
import com.juanimozo.matchpoint.ui.theme.NavyBlue
import com.juanimozo.matchpoint.presentation.components.GenericButton

@Composable
fun MainScreen(navController: NavController , viewModel: MainActivityViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // New Match Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            GenericButton(
                modifier = Modifier
                    .padding(horizontal = 64.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                ,
                color = NavyBlue,
                onClick = { viewModel.cleanState()
                    navController.navigate(Screens.NewMatch.route) },
                text = "Nuevo Partido"
            )
        }

        // History Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            GenericButton(
                modifier = Modifier
                    .padding(horizontal = 64.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                ,
                color = Green,
                onClick = { viewModel.getAllMatches()
                    navController.navigate(Screens.History.route) },
                text = "Historial"
            )
        }
    }

}
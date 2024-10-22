package com.juanimozo.matchpoint.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.juanimozo.matchpoint.R
import com.juanimozo.matchpoint.navigation.Screens
import com.juanimozo.matchpoint.presentation.history.HistoryViewModel
import com.juanimozo.matchpoint.presentation.match.MatchViewModel
import com.juanimozo.matchpoint.ui.theme.Green
import com.juanimozo.matchpoint.ui.theme.NavyBlue
import com.juanimozo.matchpoint.presentation.components.GenericButton

@Composable
fun HomeScreen(navController: NavController, matchViewModel: MatchViewModel, historyViewModel: HistoryViewModel) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background_1),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
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
                    color = Color.White,
                    onClick = {
                        matchViewModel.cleanState()
                        matchViewModel.getAllPlayers()
                        navController.navigate(Screens.NewMatch.route) },
                    text = "Nuevo Partido",
                    textColor = NavyBlue
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
                        .height(60.dp),
                    color = Color.White,
                    onClick = {
                        historyViewModel.getAllMatches()
                        historyViewModel.cleanSelectedCards()
                        historyViewModel.getAllPlayers()
                        navController.navigate(Screens.History.route)
                    },
                    text = "Historial",
                    textColor = Green
                )
            }
        }
    }
}
package com.juanimozo.matchpoint.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.juanimozo.matchpoint.presentation.match.MatchViewModel
import com.juanimozo.matchpoint.presentation.*
import com.juanimozo.matchpoint.presentation.history.HistoryScreen
import com.juanimozo.matchpoint.presentation.history.HistoryViewModel
import com.juanimozo.matchpoint.presentation.match.CurrentMatchScreen
import com.juanimozo.matchpoint.presentation.HomeScreen

@Composable
fun Navigation(navController: NavHostController, matchViewModel: MatchViewModel, historyViewModel: HistoryViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screens.Main.route
    ) {
        // Main Screen
        composable(Screens.Main.route) {
            HomeScreen(navController = navController, matchViewModel = matchViewModel, historyViewModel = historyViewModel)
        }

        // New Match Screen
        composable(Screens.NewMatch.route) {
            NewMatchScreen(navController = navController, viewModel = matchViewModel)
        }

        // History Screen
        composable(Screens.History.route) {
            HistoryScreen(navController = navController, matchViewModel = matchViewModel, historyViewModel = historyViewModel)
        }

        // Current Match Screen
        composable(Screens.CurrentMatch.route) {
            CurrentMatchScreen(navController = navController, viewModel = matchViewModel)
        }

        // Result Screen
        composable(Screens.Result.route) {
            ResultScreen(navController = navController, viewModel = matchViewModel)
        }

    }
}
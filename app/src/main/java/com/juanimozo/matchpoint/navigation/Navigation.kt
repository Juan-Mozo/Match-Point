package com.juanimozo.matchpoint.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.juanimozo.matchpoint.MainActivityViewModel
import com.juanimozo.matchpoint.presentation.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Navigation(navController: NavHostController, viewModel: MainActivityViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screens.Main.route
    ) {
        // Main Screen
        composable(Screens.Main.route) {
            MainScreen(navController = navController, viewModel = viewModel)
        }

        // New Match Screen
        composable(Screens.NewMatch.route) {
            NewMatchScreen(navController = navController, viewModel = viewModel)
        }

        // History Screen
        composable(Screens.History.route) {
            HistoryScreen(navController = navController, viewModel = viewModel)
        }

        // Current Match Screen
        composable(Screens.CurrentMatch.route) {
            CurrentMatchScreen(navController = navController, viewModel = viewModel)
        }

        // Result Screen
        composable(Screens.Result.route) {
            ResultScreen(navController = navController, viewModel = viewModel)
        }

    }
}
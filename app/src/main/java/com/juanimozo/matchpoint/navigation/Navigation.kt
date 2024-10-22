package com.juanimozo.matchpoint.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.juanimozo.matchpoint.presentation.match.MatchViewModel
import com.juanimozo.matchpoint.presentation.history.HistoryScreen
import com.juanimozo.matchpoint.presentation.history.HistoryViewModel
import com.juanimozo.matchpoint.presentation.match.CurrentMatchScreen
import com.juanimozo.matchpoint.presentation.HomeScreen
import com.juanimozo.matchpoint.presentation.match.NewMatchScreen
import com.juanimozo.matchpoint.presentation.match.ResultScreen

@Composable
fun Navigation(navController: NavHostController, matchViewModel: MatchViewModel, historyViewModel: HistoryViewModel) {
    NavHost(
        navController = navController,
        startDestination = Screens.Main.route
    ) {
        // Main Screen
        composable(
            route = Screens.Main.route,
            enterTransition = {
                slideInHorizontally()
            },
            exitTransition = {
                slideOutHorizontally()
            }
        ) {
            HomeScreen(navController = navController, matchViewModel = matchViewModel, historyViewModel = historyViewModel)
        }

        // New Match Screen
        composable(
            Screens.NewMatch.route,
            enterTransition = {
                slideInHorizontally()
            },
            exitTransition = {
                slideOutHorizontally()
            }
        ) {
            NewMatchScreen(navController = navController, viewModel = matchViewModel)
        }

        // History Screen
        composable(
            route = Screens.History.route,
            enterTransition = {
                slideInHorizontally()
            },
            exitTransition = {
                slideOutHorizontally()
            }
        ) {
            HistoryScreen(navController = navController, matchViewModel = matchViewModel, historyViewModel = historyViewModel)
        }

        // Current Match Screen
        composable(
            route = Screens.CurrentMatch.route,
            enterTransition = {
                slideInHorizontally()
            },
            exitTransition = {
                slideOutHorizontally()
            }
        ) {
            CurrentMatchScreen(navController = navController, viewModel = matchViewModel)
        }

        // Result Screen
        composable(
            route = Screens.Result.route,
            enterTransition = {
                slideInHorizontally()
            },
            exitTransition = {
                slideOutHorizontally()
            }
        ) {
            ResultScreen(navController = navController, viewModel = matchViewModel)
        }

    }
}
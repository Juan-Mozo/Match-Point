package com.juanimozo.matchpoint.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
        startDestination = Screens.Main.route,
        enterTransition = {
            expandIn(tween(700, easing = LinearEasing))
        }
    ) {
        // Main Screen
        composable(
            route = Screens.Main.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            }
        ) {
            HomeScreen(navController = navController, matchViewModel = matchViewModel, historyViewModel = historyViewModel)
        }

        // New Match Screen
        composable(
            Screens.NewMatch.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            }
        ) {
            NewMatchScreen(navController = navController, viewModel = matchViewModel)
        }

        // History Screen
        composable(
            route = Screens.History.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            }
        ) {
            HistoryScreen(navController = navController, matchViewModel = matchViewModel, historyViewModel = historyViewModel)
        }

        // Current Match Screen
        composable(
            route = Screens.CurrentMatch.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            }
        ) {
            CurrentMatchScreen(navController = navController, viewModel = matchViewModel)
        }

        // Result Screen
        composable(
            route = Screens.Result.route,
            arguments = listOf(
                navArgument("isNewMatch") {
                    defaultValue = true
                    type = NavType.BoolType
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(700)
                )
            }
        ) { navBackStackEntry ->
            val isNewMatch = navBackStackEntry.arguments?.getBoolean("isNewMatch")
            if (isNewMatch != null) {
                ResultScreen(
                    navController = navController,
                    viewModel = matchViewModel,
                    isNewMatch = isNewMatch
                )
            }
        }

    }
}
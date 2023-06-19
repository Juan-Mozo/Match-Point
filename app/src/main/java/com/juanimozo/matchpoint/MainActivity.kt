package com.juanimozo.matchpoint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.juanimozo.matchpoint.navigation.Navigation
import com.juanimozo.matchpoint.presentation.history.HistoryViewModel
import com.juanimozo.matchpoint.ui.theme.MatchPointTheme
import com.juanimozo.matchpoint.presentation.match.MatchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MatchPointTheme {

                val matchViewModel: MatchViewModel = hiltViewModel()
                val historyViewModel: HistoryViewModel = hiltViewModel()
                val navController = rememberNavController()

                Scaffold { padding ->
                    Column(modifier = Modifier.padding(padding)) {
                        Navigation(navController = navController, matchViewModel = matchViewModel, historyViewModel = historyViewModel)
                    }
                }
            }
        }
    }
}

package com.juanimozo.matchpoint.presentation.history

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.juanimozo.matchpoint.data.database.entity.Match
import com.juanimozo.matchpoint.domain.use_cases.ResultUseCases
import com.juanimozo.matchpoint.presentation.history.event.HistoryEvents
import com.juanimozo.matchpoint.presentation.history.state.PastMatchesState
import com.juanimozo.matchpoint.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val resultUseCases: ResultUseCases
): ViewModel() {

    private val _pastResultsState = mutableStateOf(PastMatchesState())
    val pastResultsState: State<PastMatchesState> = _pastResultsState

    private var getMatchesJob: Job? = null
    private var updateMatchesJob: Job? = null

    fun getAllMatches() {
        getMatchesJob?.cancel()
        getMatchesJob = resultUseCases.getMatchesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data != null) {
                        _pastResultsState.value = pastResultsState.value.copy(
                            matches = result.data
                        )
                    }
                }
                is Resource.Error -> {}
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun deleteMatch(match: Match) {
        // Delete Match
        updateMatchesJob?.cancel()
        updateMatchesJob = resultUseCases.updateMatchesUseCase.deleteMatch(match).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.e("VM", "Saved Successfully")
                }
                is Resource.Error -> {
                    Log.e("VM", result.message!!)
                }
            }

        }.launchIn(CoroutineScope(Dispatchers.IO))

        // Update List
        _pastResultsState.value = pastResultsState.value.copy(
            matches = _pastResultsState.value.matches - match
        )
    }

    fun onHistoryEvent(event: HistoryEvents) {
        when (event) {
            is HistoryEvents.ExpandCard -> {
                if (_pastResultsState.value.revealedCard != event.match) {
                    // Add card to revealed cards
                    _pastResultsState.value = pastResultsState.value.copy(
                        revealedCard = event.match
                    )
                }
            }
            is HistoryEvents.CollapseCard -> {
                if (_pastResultsState.value.revealedCard == event.match) {
                    // Add card to revealed cards
                    _pastResultsState.value = pastResultsState.value.copy(
                        revealedCard = null
                    )
                }
            }
        }
    }

    fun cleanSelectedCards() {
        _pastResultsState.value = pastResultsState.value.copy(
            revealedCard = null
        )
    }

}
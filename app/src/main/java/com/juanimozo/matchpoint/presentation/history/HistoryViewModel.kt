package com.juanimozo.matchpoint.presentation.history

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.juanimozo.matchpoint.domain.model.MatchWithTeamsModel
import com.juanimozo.matchpoint.domain.model.PlayerModel
import com.juanimozo.matchpoint.domain.use_cases.ResultUseCases
import com.juanimozo.matchpoint.presentation.history.event.HistoryEvents
import com.juanimozo.matchpoint.presentation.history.state.HistoryState
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

    private val _historyState = mutableStateOf(HistoryState())
    val historyState: State<HistoryState> = _historyState

    private var getMatchesJob: Job? = null
    private var updateMatchesJob: Job? = null
    private var searchPlayersJob: Job? = null

    fun getAllMatches() {
        getMatchesJob?.cancel()
        getMatchesJob = resultUseCases.getMatchesUseCase.getAllMatches().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data != null) {
                        _historyState.value = historyState.value.copy(
                            matches = result.data
                        )
                    }
                }
                is Resource.Error -> {}
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun deleteMatch(match: MatchWithTeamsModel) {
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
        _historyState.value = historyState.value.copy(
            matches = _historyState.value.matches - match
        )
    }

    fun onHistoryEvent(event: HistoryEvents) {
        when (event) {
            is HistoryEvents.ExpandCard -> {
                if (_historyState.value.revealedCard != event.match) {
                    // Add card to revealed cards
                    _historyState.value = historyState.value.copy(
                        revealedCard = event.match
                    )
                }
            }
            is HistoryEvents.CollapseCard -> {
                if (_historyState.value.revealedCard == event.match) {
                    // Add card to revealed cards
                    _historyState.value = historyState.value.copy(
                        revealedCard = null
                    )
                }
            }
            is HistoryEvents.SelectPlayer -> {
                _historyState.value = historyState.value.copy(
                    playerInFilter = event.player ?: PlayerModel(),
                    playerTextField = event.player?.name ?: ""
                )
                getMatchesByPlayer(_historyState.value.playerInFilter?.id ?: 0)
            }
            is HistoryEvents.UpdatePlayerName -> {
                // Show list of players
                if (event.name.isBlank()) {
                    getAllMatches()
                    getAllPlayers()
                } else {
                    getPlayersByName(event.name)
                }
                _historyState.value = historyState.value.copy(
                    playerTextField = event.name,
                    playerInFilter = PlayerModel()
                )
            }
        }
    }

    fun cleanSelectedCards() {
        _historyState.value = historyState.value.copy(
            revealedCard = null
        )
    }

    fun getAllPlayers() {
        searchPlayersJob?.cancel()
        searchPlayersJob = resultUseCases.playerUseCase.getAllPlayers().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _historyState.value = historyState.value.copy(
                        playersFound = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    Log.e("History VM", result.message!!)
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    private fun getPlayersByName(name: String) {
        searchPlayersJob?.cancel()
        searchPlayersJob = resultUseCases.playerUseCase.searchPlayersByName(name).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _historyState.value = historyState.value.copy(
                        playersFound = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    Log.e("History VM", result.message!!)
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    private fun getMatchesByPlayer(id : Int) {
        getMatchesJob?.cancel()
        getMatchesJob = resultUseCases.getMatchesUseCase.getMatchesByPlayer(id)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data != null) {
                            _historyState.value = historyState.value.copy(
                                matches = result.data
                            )
                        }
                    }
                is Resource.Error -> {
                    Log.e("History VM", result.message!!)
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

}
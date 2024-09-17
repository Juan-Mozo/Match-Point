package com.juanimozo.matchpoint.presentation.match

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.juanimozo.matchpoint.domain.model.MatchWithTeamsModel
import com.juanimozo.matchpoint.domain.model.PlayerModel
import com.juanimozo.matchpoint.domain.model.TeamModel
import com.juanimozo.matchpoint.domain.use_cases.ResultUseCases
import com.juanimozo.matchpoint.navigation.Screens
import com.juanimozo.matchpoint.presentation.match.event.UserEvents
import com.juanimozo.matchpoint.presentation.match.event.NewMatchEvents
import com.juanimozo.matchpoint.presentation.match.state.ChronometerState
import com.juanimozo.matchpoint.presentation.match.state.CurrentMatchState
import com.juanimozo.matchpoint.presentation.match.state.NewMatchState
import com.juanimozo.matchpoint.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val resultUseCases: ResultUseCases
): ViewModel() {

    private val _currentMatchState = mutableStateOf(CurrentMatchState())
    val currentMatchState: State<CurrentMatchState> = _currentMatchState

    private val _chronometerState = mutableStateOf(ChronometerState())
    val chronometerState: State<ChronometerState> = _chronometerState

    private val _newMatchState = mutableStateOf(NewMatchState())
    val newMatchState: State<NewMatchState> = _newMatchState

    private val _userEventsState = MutableSharedFlow<UserEvents>()
    val userEventsState = _userEventsState.asSharedFlow()

    private var updateMatchesJob: Job? = null
    private var searchPlayersJob: Job? = null

    // Match
    fun startMatch(currentTime: Long, currentDate: String, simplifiedDate: String, navController: NavController) {
        val v = _newMatchState.value
        // Check that no player field is empty
        if (v.matchType == MatchType.SINGLES && v.player1.player == null || v.player2.player == null) {
            showSnackbar()
        } else if (v.matchType == MatchType.DOUBLES && v.player1.player == null || v.player2.player == null ||
                v.player3.player == null || v.player4.player == null) {
            showSnackbar()
        } else {
            // Update State
            _currentMatchState.value = currentMatchState.value.copy(
                match = MatchWithTeamsModel(
                    team1 = TeamModel(_newMatchState.value.player1.player!!, _newMatchState.value.player3.player),
                    team2 = TeamModel(_newMatchState.value.player2.player!!, _newMatchState.value.player4.player),
                    courtName = _newMatchState.value.courtName,
                    date = currentDate,
                    simplifiedDate = simplifiedDate
                )
            )
            // Start Chronometer
            _chronometerState.value = chronometerState.value.copy(
                isRunning = true,
                startTime = currentTime
            )
            // Navigate to Match
            navController.navigate(Screens.CurrentMatch.route)
        }
    }

    fun endMatch() {
        // Stop Chronometer
        _chronometerState.value = chronometerState.value.copy(
            isRunning = false,
        )
        // Save Match
        saveMatch()
        // End Match
        viewModelScope.launch {
            _userEventsState.emit(UserEvents(isMatchEnded = true))
        }
    }

    private fun saveMatch() {
        val m = currentMatchState.value

        val numberOfSets = when (m.currentSet) {
            is Sets.FirstSet -> 1
            is Sets.SecondSet -> 2
            is Sets.ThirdSet -> 3
        }

        // Update last set played when match didn't end with 3 complete sets
        var firstSetTeam1 = m.match.set1Team1
        var firstSetTeam2 = m.match.set1Team2
        var secondSetTeam1 = m.match.set2Team1
        var secondSetTeam2 = m.match.set2Team2
        var thirdSetTeam1 = m.match.set3Team1
        var thirdSetTeam2 = m.match.set3Team2

        when (m.currentSet) {
            Sets.FirstSet -> {
                firstSetTeam1 = m.currentSetTeam1
                firstSetTeam2 = m.currentSetTeam2
            }
            Sets.SecondSet -> {
                secondSetTeam1 = m.currentSetTeam1
                secondSetTeam2 = m.currentSetTeam2
            }
            Sets.ThirdSet -> {
                thirdSetTeam1 = m.currentSetTeam1
                thirdSetTeam2 = m.currentSetTeam2
            }
        }
        updateLastSetToState()

        // Update current match with final results
        _currentMatchState.value = currentMatchState.value.copy(
            match = currentMatchState.value.match.copy(
                duration = chronometerState.value.elapsedTime,
                numberOfSets = numberOfSets,
                set1Team1 = firstSetTeam1,
                set1Team2 = firstSetTeam2,
                set2Team1 = secondSetTeam1,
                set2Team2 = secondSetTeam2,
                set3Team1 = thirdSetTeam1,
                set3Team2 = thirdSetTeam2,
            )
        )

        // Save Values
        updateMatchesJob?.cancel()
        updateMatchesJob = resultUseCases.updateMatchesUseCase.insertMatch(
            _currentMatchState.value.match
        ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.e("VM", "Saved Successfully")
                    }
                    is Resource.Error -> {
                        Log.e("VM", result.message!!)
                    }
                }
            }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    // Registration
    fun onRegistrationEvent(event: NewMatchEvents) {
        when (event) {
            is NewMatchEvents.UpdateCourtName -> {
                _newMatchState.value = newMatchState.value.copy(
                    courtName = event.name
                )
            }

            is NewMatchEvents.UpdateCountPoints -> {
                _newMatchState.value = newMatchState.value.copy(
                    countPoints = event.isSelected
                )
            }

            is NewMatchEvents.UpdateMatchType -> {
                _newMatchState.value = newMatchState.value.copy(
                    matchType = event.matchType
                )
            }

            is NewMatchEvents.UpdatePlayerName -> {
                // Show list of players
                if (event.name.isBlank()) {
                    getAllPlayers()
                } else {
                    getPlayersByName(event.name)
                }

                when (event.playerNumber) {
                    1 -> {
                        _newMatchState.value = newMatchState.value.copy(
                            player1 = newMatchState.value.player1.copy(
                                nameSearch = event.name,
                                isListOfPlayersCollapsed = true
                            )
                        )
                    }
                    2 -> {
                        _newMatchState.value = newMatchState.value.copy(
                            player2 = newMatchState.value.player2.copy(
                                nameSearch = event.name,
                                isListOfPlayersCollapsed = true
                            )
                        )
                    }
                    3 -> {
                        _newMatchState.value = newMatchState.value.copy(
                            player3 = newMatchState.value.player3.copy(
                                nameSearch = event.name,
                                isListOfPlayersCollapsed = true
                            )
                        )
                    }
                    4 -> {
                        _newMatchState.value = newMatchState.value.copy(
                            player4 = newMatchState.value.player4.copy(
                                nameSearch = event.name,
                                isListOfPlayersCollapsed = true
                            )
                        )
                    }
                }
            }

            is NewMatchEvents.UpdateNewPlayerName -> {
                _newMatchState.value = newMatchState.value.copy(
                    newPlayerName = event.name
                )
            }

            is NewMatchEvents.SelectPlayer -> {
                when (event.playerNumber) {
                    1 -> {
                        _newMatchState.value = newMatchState.value.copy(
                            player1 = newMatchState.value.player1.copy(
                                nameSearch = event.player?.name ?: "",
                                player = event.player,
                                isListOfPlayersCollapsed = false
                            )
                        )
                    }
                    2 -> {
                        _newMatchState.value = newMatchState.value.copy(
                            player2 = newMatchState.value.player2.copy(
                                nameSearch = event.player?.name ?: "",
                                player = event.player,
                                isListOfPlayersCollapsed = false
                            )
                        )
                    }
                    3 -> {
                        _newMatchState.value = newMatchState.value.copy(
                            player3 = newMatchState.value.player3.copy(
                                nameSearch = event.player?.name ?: "",
                                player = event.player,
                                isListOfPlayersCollapsed = false
                            )
                        )
                    }
                    4 -> {
                        _newMatchState.value = newMatchState.value.copy(
                            player4 = newMatchState.value.player4.copy(
                                nameSearch = event.player?.name ?: "",
                                player = event.player,
                                isListOfPlayersCollapsed = false
                            )
                        )
                    }
                }
            }

            is NewMatchEvents.ManageNewPlayerPopup -> {
                _newMatchState.value = newMatchState.value.copy(
                    showPopup = event.showPupup,
                    playerIndexNewPlayer = event.playerNumber
                )
            }
        }
    }

    // Chronometer
    fun updateChronometer(v: Long) {
        _chronometerState.value = chronometerState.value.copy(
            elapsedTime = v
        )
    }

    // State
    fun cleanState() {
        _currentMatchState.value = CurrentMatchState()
        _newMatchState.value = NewMatchState()
        _chronometerState.value = ChronometerState()
    }

    fun updateCurrentMatchState(match: MatchWithTeamsModel) {
        _currentMatchState.value = currentMatchState.value.copy(
            match = match
        )
    }

    // Points
    fun sumPoint(team: Teams) {
        if (newMatchState.value.countPoints) {
            handleSumPoints(team)
        } else {
            handleSumGames(team)
        }
    }

    private fun handleSumPoints(team: Teams) {
        val m = currentMatchState.value
        when (team) {
            is Teams.Team1 -> {
                when (m.team1Points) {
                    is Points.Zero -> {
                        _currentMatchState.value = m.copy(
                            team1Points = Points.Fifteen()
                        )
                    }
                    is Points.Fifteen -> {
                        _currentMatchState.value = m.copy(
                            team1Points = Points.Thirty()
                        )
                    }
                    is Points.Thirty -> {
                        _currentMatchState.value = m.copy(
                            team1Points = Points.Forty()
                        )
                    }
                    is Points.Forty -> {
                        when (m.team2Points) {
                            is Points.Forty -> {
                                _currentMatchState.value = m.copy(
                                    team1Points = Points.AdIn()
                                )
                            }
                            is Points.AdOut -> {
                                _currentMatchState.value = m.copy(
                                    team1Points = Points.Forty(),
                                    team2Points = Points.Forty()
                                )
                            }
                            else -> {
                                handleSumGames(Teams.Team1())
                                restorePointsToZero()
                            }
                        }
                    }
                    is Points.AdIn -> {
                        handleSumGames(Teams.Team1())
                        restorePointsToZero()
                    }
                    is Points.AdOut -> {}
                }
            }
            is Teams.Team2 -> {
                when (m.team2Points) {
                    is Points.Zero -> {
                        _currentMatchState.value = m.copy(
                            team2Points = Points.Fifteen()
                        )
                    }
                    is Points.Fifteen -> {
                        _currentMatchState.value = m.copy(
                            team2Points = Points.Thirty()
                        )
                    }
                    is Points.Thirty -> {
                        _currentMatchState.value = m.copy(
                            team2Points = Points.Forty()
                        )
                    }
                    is Points.Forty -> {
                        when (m.team1Points) {
                            is Points.Forty -> {
                                _currentMatchState.value = m.copy(
                                    team2Points = Points.AdOut()
                                )
                            }
                            is Points.AdIn -> {
                                _currentMatchState.value = m.copy(
                                    team1Points = Points.Forty(),
                                    team2Points = Points.Forty()
                                )
                            }
                            else -> {
                                handleSumGames(Teams.Team2())
                                restorePointsToZero()
                            }
                        }
                    }
                    is Points.AdOut -> {
                        handleSumGames(Teams.Team2())
                        restorePointsToZero()
                    }
                    is Points.AdIn -> {}
                }
            }
        }
    }

    fun restPoint(team: Teams) {
        if (newMatchState.value.countPoints) {
            when (team) {
                is Teams.Team1 -> {
                    when (currentMatchState.value.team1Points) {
                        is Points.Fifteen -> {
                            _currentMatchState.value = currentMatchState.value.copy(
                                team1Points = Points.Zero()
                            )
                        }
                        is Points.Thirty -> {
                            _currentMatchState.value = currentMatchState.value.copy(
                                team1Points = Points.Fifteen()
                            )
                        }
                        is Points.Forty -> {
                            _currentMatchState.value = currentMatchState.value.copy(
                                team1Points = Points.Thirty()
                            )
                        }
                        else -> {}
                    }
                }
                is Teams.Team2 -> {
                    when (currentMatchState.value.team2Points) {
                        is Points.Fifteen -> {
                            _currentMatchState.value = currentMatchState.value.copy(
                                team2Points = Points.Zero()
                            )
                        }
                        is Points.Thirty -> {
                            _currentMatchState.value = currentMatchState.value.copy(
                                team2Points = Points.Fifteen()
                            )
                        }
                        is Points.Forty -> {
                            _currentMatchState.value = currentMatchState.value.copy(
                                team2Points = Points.Thirty()
                            )
                        }
                        else -> {}
                    }
                }
            }
        } else {
            when (team) {
                is Teams.Team1 -> {
                    if (currentMatchState.value.currentSetTeam1 > 0) {
                        _currentMatchState.value = currentMatchState.value.copy(
                            currentSetTeam1 = currentMatchState.value.currentSetTeam1 - 1
                        )
                    }
                }
                is Teams.Team2 -> {
                    if (currentMatchState.value.currentSetTeam2 > 0) {
                        _currentMatchState.value = currentMatchState.value.copy(
                            currentSetTeam2 = currentMatchState.value.currentSetTeam2 - 1
                        )
                    }
                }

            }
        }
    }

    private fun handleSumGames(team: Teams) {
        if (currentMatchState.value.currentSetTeam1 == 6 && currentMatchState.value.currentSetTeam2 == 6) {
            // Result is 6 - 6
            _currentMatchState.value = currentMatchState.value.copy(
                isTieBreak = true,
                currentSetTeam1 = 0,
                currentSetTeam2 = 0
            )
        } else {
            when (team) {
                is Teams.Team1 -> {
                    if (currentMatchState.value.currentSetTeam1 == 6 && currentMatchState.value.currentSetTeam2 == 5) {
                        // Result is 7 - 5
                        finishSet(Teams.Team1())
                    } else if (currentMatchState.value.currentSetTeam1 == 5 && currentMatchState.value.currentSetTeam2 < 5) {
                        // Result is 6 - 4
                        finishSet(Teams.Team1())
                    } else {
                        // Result is anything else
                        _currentMatchState.value = currentMatchState.value.copy(
                            currentSetTeam1 = currentMatchState.value.currentSetTeam1 + 1
                        )
                    }
                }
                is Teams.Team2 -> {
                    if (currentMatchState.value.currentSetTeam2 == 6 && currentMatchState.value.currentSetTeam1 == 5) {
                        // Result is 5 - 7
                        finishSet(Teams.Team2())
                    } else if (currentMatchState.value.currentSetTeam2 == 5 && currentMatchState.value.currentSetTeam1 < 5) {
                        // Result is 4 - 6
                        finishSet(Teams.Team2())
                    } else {
                        // Result is anything else
                        _currentMatchState.value = currentMatchState.value.copy(
                            currentSetTeam2 = currentMatchState.value.currentSetTeam2 + 1
                        )
                    }
                }
            }
        }
    }

    private fun finishSet(winnerTeam: Teams) {
        val m = currentMatchState.value
        when (winnerTeam) {
            is Teams.Team1 -> {
                when (m.currentSet) {
                    Sets.FirstSet -> {
                        _currentMatchState.value = m.copy(
                            currentSet = Sets.SecondSet,
                            currentSetTeam1 = 0,
                            currentSetTeam2 = 0,
                            match = m.match.copy(
                                set1Team1 = m.currentSetTeam1 + 1,
                                set1Team2 = m.currentSetTeam2
                            )
                        )
                    }
                    Sets.SecondSet -> {
                        _currentMatchState.value = m.copy(
                            currentSet = Sets.ThirdSet,
                            currentSetTeam1 = 0,
                            currentSetTeam2 = 0,
                            match = m.match.copy(
                                set2Team1 = m.currentSetTeam1 + 1,
                                set2Team2 = m.currentSetTeam2
                            )
                        )
                    }
                    Sets.ThirdSet -> {
                        _currentMatchState.value = m.copy(
                            match = m.match.copy(
                                set3Team1 = m.currentSetTeam1 + 1,
                                set3Team2 = m.currentSetTeam2
                            )
                        )
                        endMatch()
                    }
                }
            }
            is Teams.Team2 -> {
                when (m.currentSet) {
                    Sets.FirstSet -> {
                        _currentMatchState.value = m.copy(
                            currentSet = Sets.SecondSet,
                            currentSetTeam1 = 0,
                            currentSetTeam2 = 0,
                            match = m.match.copy(
                                set1Team1 = m.currentSetTeam1,
                                set1Team2 = m.currentSetTeam2 + 1
                            )
                        )
                    }
                    Sets.SecondSet -> {
                        _currentMatchState.value = m.copy(
                            currentSet = Sets.ThirdSet,
                            currentSetTeam1 = 0,
                            currentSetTeam2 = 0,
                            match = m.match.copy(
                                set2Team1 = m.currentSetTeam1,
                                set2Team2 = m.currentSetTeam2 + 1
                            )
                        )
                    }
                    Sets.ThirdSet -> {
                        _currentMatchState.value = m.copy(
                            match = m.match.copy(
                                set3Team1 = m.currentSetTeam1,
                                set3Team2 = m.currentSetTeam2 + 1
                            )
                        )
                        endMatch()
                    }
                }
            }
        }
    }

    private fun restorePointsToZero() {
        _currentMatchState.value = currentMatchState.value.copy(
            team1Points = Points.Zero(),
            team2Points = Points.Zero()
        )
    }

    private fun updateLastSetToState() {
        val m = currentMatchState.value
        when (m.currentSet) {
            is Sets.FirstSet -> {
                _currentMatchState.value = m.copy(
                    match = m.match.copy(
                        set1Team1 = m.currentSetTeam1,
                        set1Team2 = m.currentSetTeam2
                    )
                )
            }
            is Sets.SecondSet -> {
                _currentMatchState.value = m.copy(
                    match = m.match.copy(
                        set2Team1 = m.currentSetTeam1,
                        set2Team2 = m.currentSetTeam2
                    )
                )
            }
            is Sets.ThirdSet -> {
                _currentMatchState.value = m.copy(
                    match = m.match.copy(
                        set3Team1 = m.currentSetTeam1,
                        set3Team2 = m.currentSetTeam2
                    )
                )
            }
        }
    }

    private fun getAllPlayers() {
        searchPlayersJob?.cancel()
        searchPlayersJob = resultUseCases.playerUseCase.getAllPlayers().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _newMatchState.value = newMatchState.value.copy(
                        playersFound = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    Log.e("VM", result.message!!)
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    private fun getPlayersByName(name: String) {
        searchPlayersJob?.cancel()
        searchPlayersJob = resultUseCases.playerUseCase.searchPlayersByName(name).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _newMatchState.value = newMatchState.value.copy(
                        playersFound = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    Log.e("VM", result.message!!)
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    private fun searchPlayer(name: String?) {
        searchPlayersJob?.cancel()
        searchPlayersJob = resultUseCases.playerUseCase.searchPlayersByName(name).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _newMatchState.value = newMatchState.value.copy(
                        playersFound = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    Log.e("VM", result.message!!)
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun addNewPlayer() {
        // Upload player to database
        searchPlayersJob?.cancel()
        searchPlayersJob = resultUseCases.playerUseCase.createNewPlayer(
            PlayerModel(name = _newMatchState.value.newPlayerName)
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.e("VM", "Saved Successfully")
                }
                is Resource.Error -> {
                    Log.e("VM", result.message!!)
                }
            }
        }.launchIn(CoroutineScope(Dispatchers.IO))

        // Clear state
        _newMatchState.value = newMatchState.value.copy(
            newPlayerName = ""
        )
    }

    fun showSnackbar() {
        viewModelScope.launch {
            _userEventsState.emit(UserEvents(isMessageShowed = true))
        }
    }

}
package com.juanimozo.matchpoint.domain.use_cases

import com.juanimozo.matchpoint.domain.model.MatchWithTeamsModel
import com.juanimozo.matchpoint.domain.repository.ResultDatabaseRepository
import com.juanimozo.matchpoint.util.Resource
import kotlinx.coroutines.flow.Flow

class GetMatchesUseCase(
    private val repository: ResultDatabaseRepository
) {

    fun getAllMatches(): Flow<Resource<List<MatchWithTeamsModel>>> {
        return repository.getAllMatches()
    }

    fun getMatchesByPlayer(id: Int): Flow<Resource<List<MatchWithTeamsModel>>> {
        return repository.getAllMatches()
    }

    fun getMatchesByTeam(player1Id: Int, player2Id: Int): Flow<Resource<List<MatchWithTeamsModel>>> {
        return repository.getAllMatches()
    }

}
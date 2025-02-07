package com.juanimozo.matchpoint.domain.use_cases

import com.juanimozo.matchpoint.domain.model.MatchWithTeamsModel
import com.juanimozo.matchpoint.domain.repository.ResultDatabaseRepository
import com.juanimozo.matchpoint.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdateMatchesUseCase(
    private val repository: ResultDatabaseRepository
) {
    fun insertMatch(match: MatchWithTeamsModel): Flow<Resource<Boolean>> {
        return repository.insertMatch(match)
    }

    fun deleteMatch(match: MatchWithTeamsModel): Flow<Resource<Boolean>> {
        return repository.deleteMatch(match)
    }
}
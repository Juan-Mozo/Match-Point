package com.juanimozo.matchpoint.domain.use_cases

import com.juanimozo.matchpoint.data.database.entity.Match
import com.juanimozo.matchpoint.domain.repository.ResultDatabaseRepository
import com.juanimozo.matchpoint.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdateMatchesUseCase(
    private val repository: ResultDatabaseRepository
) {
    fun insertMatch(match: Match): Flow<Resource<Boolean>> {
        return repository.insertMatch(match)
    }

    fun deleteMatch(match: Match): Flow<Resource<Boolean>> {
        return repository.deleteMatch(match)
    }
}
package com.juanimozo.matchpoint.domain.use_cases

import com.juanimozo.matchpoint.data.database.entity.Match
import com.juanimozo.matchpoint.domain.repository.ResultDatabaseRepository
import com.juanimozo.matchpoint.util.Resource
import kotlinx.coroutines.flow.Flow

class GetMatchesUseCase(
    private val repository: ResultDatabaseRepository
) {
    operator fun invoke(): Flow<Resource<List<Match>>> {
        return repository.getAllMatches()
    }
}
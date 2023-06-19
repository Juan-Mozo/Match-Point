package com.juanimozo.matchpoint.domain.repository

import com.juanimozo.matchpoint.data.database.entity.Match
import com.juanimozo.matchpoint.util.Resource
import kotlinx.coroutines.flow.Flow

interface ResultDatabaseRepository {

    fun insertMatch(match: Match): Flow<Resource<Boolean>>

    fun getAllMatches(): Flow<Resource<List<Match>>>

    fun deleteMatch(match: Match): Flow<Resource<Boolean>>

}
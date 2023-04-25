package com.juanimozo.matchpoint.data.repository

import com.juanimozo.matchpoint.data.database.ResultDao
import com.juanimozo.matchpoint.data.database.entity.Match
import com.juanimozo.matchpoint.domain.repository.ResultDatabaseRepository
import com.juanimozo.matchpoint.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ResultDatabaseRepositoryImpl(
    private val db: ResultDao
): ResultDatabaseRepository {

    override fun insertMatch(match: Match): Flow<Resource<Boolean>> = flow {
        try {
            db.insertMatch(match = match)
            emit(Resource.Success())
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }
    }

    override fun getAllMatches(): Flow<Resource<List<Match>>> = flow {
        val matches = db.getAllMatches()
        emit(Resource.Success(data = matches))
    }

    override fun deleteMatch(id: Int): Flow<Resource<Boolean>> = flow {
        try {
            db.deleteMatch(id)
            emit(Resource.Success())
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }
    }

}
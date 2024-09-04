package com.juanimozo.matchpoint.domain.use_cases

import com.juanimozo.matchpoint.domain.model.PlayerModel
import com.juanimozo.matchpoint.domain.repository.ResultDatabaseRepository
import com.juanimozo.matchpoint.util.Resource
import kotlinx.coroutines.flow.Flow

class PlayerUseCase(
    private val repository: ResultDatabaseRepository
) {

    fun signUpPlayer(player: PlayerModel): Flow<Resource<Boolean>> {
        return repository.insertPlayer(player)
    }

    fun getPlayerById(id: Int): Flow<Resource<PlayerModel>> {
        return repository.getPlayerById(id)
    }

}
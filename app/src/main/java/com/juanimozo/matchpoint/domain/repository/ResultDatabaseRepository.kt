package com.juanimozo.matchpoint.domain.repository

import com.juanimozo.matchpoint.domain.model.MatchWithTeamsModel
import com.juanimozo.matchpoint.domain.model.PlayerModel
import com.juanimozo.matchpoint.util.Resource
import kotlinx.coroutines.flow.Flow

interface ResultDatabaseRepository {

    fun insertMatch(match: MatchWithTeamsModel): Flow<Resource<Boolean>>

    fun deleteMatch(match: MatchWithTeamsModel): Flow<Resource<Boolean>>

    fun insertPlayer(player: PlayerModel): Flow<Resource<Boolean>>

    fun getPlayerById(id: Int): Flow<Resource<PlayerModel>>

    fun getMatchById(id: Int): Flow<Resource<MatchWithTeamsModel>>

    fun searchPlayersByName(name: String?): Flow<Resource<List<PlayerModel>>>

    fun getAllMatches(): Flow<Resource<List<MatchWithTeamsModel>>>

    fun getMatchesByPlayer(id: Int): Flow<Resource<List<MatchWithTeamsModel>>>

    fun getMatchesByTeam(player1Id: Int, player2Id: Int): Flow<Resource<List<MatchWithTeamsModel>>>

    fun getAllPlayers(): Flow<Resource<List<PlayerModel>>>

}
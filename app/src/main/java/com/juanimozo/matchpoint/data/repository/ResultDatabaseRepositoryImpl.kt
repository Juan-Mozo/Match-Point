package com.juanimozo.matchpoint.data.repository

import com.juanimozo.matchpoint.data.database.ResultDao
import com.juanimozo.matchpoint.data.database.entity.Match
import com.juanimozo.matchpoint.domain.model.MatchWithTeamsModel
import com.juanimozo.matchpoint.domain.model.PlayerModel
import com.juanimozo.matchpoint.domain.model.TeamModel
import com.juanimozo.matchpoint.domain.repository.ResultDatabaseRepository
import com.juanimozo.matchpoint.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ResultDatabaseRepositoryImpl(
    private val db: ResultDao
): ResultDatabaseRepository {

    override fun insertMatch(match: MatchWithTeamsModel): Flow<Resource<Boolean>> = flow {
        try {
            db.insertMatch(match = match.toMatchEntity())
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(data = false, message = e.message))
        }
    }

    override fun deleteMatch(match: MatchWithTeamsModel): Flow<Resource<Boolean>> = flow {
        try {
            db.deleteMatch(match = match.toMatchEntity())
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(data = false, message = e.message))
        }
    }

    override fun insertPlayer(player: PlayerModel): Flow<Resource<Boolean>> = flow {
        try {
            db.insertPlayer(player.toPlayerEntity())
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(data = false, message = e.message))
        }
    }

    override fun getPlayerById(id: Int): Flow<Resource<PlayerModel>> = flow {
        try {
            val player = db.getPlayerById(id).toPlayerModel()
            emit(Resource.Success(player))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }
    }

    override fun getMatchById(id: Int): Flow<Resource<MatchWithTeamsModel>> = flow {
        try {
            val match = transformMatch(listOf(db.getMatchById(id))).first()
            emit(Resource.Success(match))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }
    }

    override fun searchPlayersByName(name: String?): Flow<Resource<List<PlayerModel>>> = flow {
        try {
            val playersTransformed = mutableListOf<PlayerModel>()
            db.getPlayerByName(name).forEach { player ->
                playersTransformed += player.toPlayerModel()
            }
            emit(Resource.Success(playersTransformed))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }
    }

    override fun getAllMatches(): Flow<Resource<List<MatchWithTeamsModel>>> = flow {
        try {
            val matches = db.getAllMatches()
            if (!matches.isNullOrEmpty()) {
                emit(Resource.Success(transformMatch(matches)))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }
    }

    override fun getMatchesByPlayer(id: Int): Flow<Resource<List<MatchWithTeamsModel>>> = flow {
        try {
            val matches = db.getMatchesByPlayerId(id)
            if (!matches.isNullOrEmpty()) {
                emit(Resource.Success(transformMatch(matches)))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }
    }

    override fun getMatchesByTeam(player1Id: Int, player2Id: Int): Flow<Resource<List<MatchWithTeamsModel>>> = flow {
        try {
            val matches = db.getMatchesByTeam(player1Id, player2Id)
            if (!matches.isNullOrEmpty()) {
                emit(Resource.Success(transformMatch(matches)))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }
    }

    override fun getAllPlayers(): Flow<Resource<List<PlayerModel>>> = flow {
        try {
            val players = db.getPlayers().map { it.toPlayerModel() }
            if (players.isNotEmpty()) {
                emit(Resource.Success(players))
            }
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message))
        }
    }

    private fun transformMatch(matches: List<Match>): List<MatchWithTeamsModel> {
        val matchesTransformed = mutableListOf<MatchWithTeamsModel>()

        matches.forEach { match ->
            val player1 = db.getPlayerById(match.player1Id)
            val player3 = match.player3Id?.let { db.getPlayerById(it) }
            val team1 = TeamModel(player1.toPlayerModel(), player3?.toPlayerModel())

            val player2 = db.getPlayerById(match.player2Id)
            val player4 = match.player4Id?.let { db.getPlayerById(it) }
            val team2 = TeamModel(player2.toPlayerModel(), player4?.toPlayerModel())

            matchesTransformed += match.toMatchModel(team1, team2)
        }

        return matchesTransformed
    }

}
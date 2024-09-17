package com.juanimozo.matchpoint.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.juanimozo.matchpoint.data.database.entity.Match
import com.juanimozo.matchpoint.data.database.entity.Player

@Dao
interface ResultDao {

    @Insert
    fun insertMatch(match: Match)

    @Delete
    fun deleteMatch(match: Match)

    @Insert
    fun insertPlayer(player: Player)

    @Query("SELECT * FROM players WHERE id = :id")
    fun getPlayerById(id: Int): Player

    @Query("SELECT * FROM matches ORDER BY id DESC")
    fun getAllMatches(): List<Match>?

    @Query("SELECT * FROM matches WHERE (:id IN (player1Id, player2Id, player3Id, player4Id))")
    fun getMatchesByPlayerId(id: Int): List<Match>?

    @Query("SELECT * FROM matches WHERE (:player1Id IN (player1Id, player2Id) AND :player2Id IN (player1Id,player2Id)) OR (:player1Id IN (player3Id, player4Id) AND :player2Id IN (player3Id, player4Id))")
    fun getMatchesByTeam(player1Id: Int, player2Id: Int): List<Match>?

    @Query("SELECT * FROM players WHERE (name LIKE '%' || :string || '%')")
    fun getPlayerByName(string: String?): List<Player>

    @Query("SELECT * FROM players ORDER BY name DESC")
    fun getPlayers(): List<Player>
}
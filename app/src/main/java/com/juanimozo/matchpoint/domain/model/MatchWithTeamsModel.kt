package com.juanimozo.matchpoint.domain.model

import com.juanimozo.matchpoint.data.database.entity.Match

data class MatchWithTeamsModel(
    val id: Int = 0,
    val team1: TeamModel = TeamModel(PlayerModel(0, "")),
    val team2: TeamModel = TeamModel(PlayerModel(0, "")),
    val date: String = "",
    val simplifiedDate: String = "",
    val duration: Long = 0,
    val courtName: String = "",
    val numberOfSets: Int = 1,
    val set1Team1: Int = 0,
    val set1Team2: Int = 0,
    val set2Team1: Int? = null,
    val set2Team2: Int? = null,
    val set3Team1: Int? = null,
    val set3Team2: Int? = null
) {
    fun toMatchEntity(): Match {
        return Match(
            id = id,
            player1Id = team1.player1.id,
            player2Id = team2.player1.id,
            player3Id = team1.player2?.id,
            player4Id = team2.player2?.id,
            date = date,
            simplifiedDate = simplifiedDate,
            duration = duration,
            courtName = courtName,
            numberOfSets = numberOfSets,
            set1Team1 = set1Team1,
            set1Team2 = set1Team2,
            set2Team1 = set2Team1,
            set2Team2 = set2Team2,
            set3Team1 = set3Team1,
            set3Team2 = set3Team2
        )
    }
}

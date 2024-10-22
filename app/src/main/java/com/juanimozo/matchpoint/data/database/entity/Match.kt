package com.juanimozo.matchpoint.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.juanimozo.matchpoint.domain.model.MatchWithTeamsModel
import com.juanimozo.matchpoint.domain.model.TeamModel
import java.util.*

@Entity(tableName = "matches")
data class Match(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "player1Id") val player1Id: Int = 0,
    @ColumnInfo(name = "player2Id") val player2Id: Int = 1,
    @ColumnInfo(name = "player3Id") val player3Id: Int? = null,
    @ColumnInfo(name = "player4Id") val player4Id: Int? = null,
    @ColumnInfo(name = "date") val date: String = "",
    @ColumnInfo(name = "simplified_date") val simplifiedDate: String = "",
    @ColumnInfo(name = "match_duration") val duration: Long = 0,
    @ColumnInfo(name = "court_name") val courtName: String = "",
    @ColumnInfo(name = "numberOfSets") val numberOfSets: Int = 1,
    @ColumnInfo(name = "set1_team1") val set1Team1: Int = 0,
    @ColumnInfo(name = "set1_team2") val set1Team2: Int = 0,
    @ColumnInfo(name = "set2_team1") val set2Team1: Int = 0,
    @ColumnInfo(name = "set2_team2") val set2Team2: Int = 0,
    @ColumnInfo(name = "set3_team1") val set3Team1: Int = 0,
    @ColumnInfo(name = "set3_team2") val set3Team2: Int = 0,
    @ColumnInfo(name = "winner_team") val winnerTeam: Int = 0
) {
    fun toMatchModel(team1: TeamModel, team2: TeamModel): MatchWithTeamsModel {
        return MatchWithTeamsModel(
            id = id,
            team1 = team1,
            team2 = team2,
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
            set3Team2 = set3Team2,
            winnerTeam = winnerTeam
        )
    }
}
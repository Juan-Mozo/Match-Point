package com.juanimozo.matchpoint.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "matches")
data class Match(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "team1_name") val team1Name: String = "",
    @ColumnInfo(name = "team2_name") val team2Name: String = "",
    @ColumnInfo(name = "match_date") val date: String = "",
    @ColumnInfo(name = "match_duration") val duration: Long = 0,
    @ColumnInfo(name = "court_name") val courtName: String = "",
    @ColumnInfo(name = "numberOfSets") val numberOfSets: Int = 1,
    @ColumnInfo(name = "set1_team1") val set1Team1: Int = 0,
    @ColumnInfo(name = "set1_team2") val set1Team2: Int = 0,
    @ColumnInfo(name = "winner_first_set") val winnerFirstSet: Int = 0,
    @ColumnInfo(name = "set2_team1") val set2Team1: Int? = null,
    @ColumnInfo(name = "set2_team2") val set2Team2: Int? = null,
    @ColumnInfo(name = "winner_second_set") val winnerSecondSet: Int = 0,
    @ColumnInfo(name = "set3_team1") val set3Team1: Int? = null,
    @ColumnInfo(name = "set3_team2") val set3Team2: Int? = null,
    @ColumnInfo(name = "winner_third_set") val winnerThirdSet: Int = 0,
    @ColumnInfo(name = "winner_team") val winnerTeam: Int = 0
)
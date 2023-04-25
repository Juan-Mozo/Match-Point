package com.juanimozo.matchpoint.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class Game(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "set_id") val matchId: Int,
    @ColumnInfo(name = "team1_score") val team1Score: String,
    @ColumnInfo(name = "team2_score") val team2Score: String,
)
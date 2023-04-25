package com.juanimozo.matchpoint.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sets")
data class Set(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "match_id") val matchId: Int,
    @ColumnInfo(name = "team1_score") val team1Score: String,
    @ColumnInfo(name = "team2_score") val team2Score: String,
)

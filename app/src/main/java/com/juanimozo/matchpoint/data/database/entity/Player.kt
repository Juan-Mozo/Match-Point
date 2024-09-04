package com.juanimozo.matchpoint.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.juanimozo.matchpoint.domain.model.PlayerModel

@Entity(tableName = "players")
data class Player(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String
) {
    fun toPlayerModel(): PlayerModel {
        return PlayerModel(id, name)
    }
}

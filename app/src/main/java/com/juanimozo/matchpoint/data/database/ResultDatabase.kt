package com.juanimozo.matchpoint.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juanimozo.matchpoint.data.database.entity.Match
import com.juanimozo.matchpoint.data.database.entity.Player

@Database(
    entities = [
        Match::class,
        Player::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ResultDatabase: RoomDatabase() {
    abstract val dao: ResultDao
}
package com.juanimozo.matchpoint.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.juanimozo.matchpoint.data.database.entity.Match

@Dao
interface ResultDao {

    @Insert
    fun insertMatch(match: Match)

    @Query("SELECT * FROM matches ORDER BY id DESC")
    fun getAllMatches(): List<Match>?

    @Query("DELETE FROM matches WHERE id = :id")
    fun deleteMatch(id: Int)

}
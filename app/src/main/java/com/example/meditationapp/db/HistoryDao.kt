package com.example.meditationapp.db

import androidx.room.*

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(history: History)

    @Query("UPDATE History SET memo = :memo WHERE id = :id")
    fun update(id: Int, memo: String)

    @Query("SELECT * FROM history")
    fun findAll(): List<History>
}
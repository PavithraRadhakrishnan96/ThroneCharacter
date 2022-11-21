package com.example.throneapp.characterlist.model

import androidx.room.*

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg character: Characters)

    @Query("select * from app_db order by id desc")
    fun getAll(): List<Characters>

    @Query("select * from app_db")
    fun getFilm(): List<Characters>

    @Query("DELETE FROM app_db")
    fun deleteAll()
}
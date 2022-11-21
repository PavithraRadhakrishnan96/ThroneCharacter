package com.example.throneapp.characterlist.model

import androidx.room.*

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg character: Characters)

    @Query("select * from app_db order by id desc")
    fun getAllRecords(): List<Characters>
}
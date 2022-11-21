package com.example.throneapp.characterlist.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Characters::class], version = 1)
abstract class CharacterDatabase : RoomDatabase() {

    abstract fun getCharacterDAO(): CharacterDao

}

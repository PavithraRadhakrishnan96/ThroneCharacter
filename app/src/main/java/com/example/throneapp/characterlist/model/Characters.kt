package com.example.throneapp.characterlist.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_db")
data class Characters(@PrimaryKey(autoGenerate = false)var id:Int?=null, var firstName:String?=null, var lastName:String?=null
                      , var fullName:String?=null, var title:String?=null, var family:String?=null, var image:String?=null,
                      var imageUrl:String?=null)
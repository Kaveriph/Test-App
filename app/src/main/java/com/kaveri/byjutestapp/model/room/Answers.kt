package com.kaveri.byjutestapp.model.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Answers")
class Answers(@PrimaryKey @ColumnInfo(name="id") @NonNull val id: String,
              val type: String, val qno:Int, val qStr: String, val ans:String, val imgLoc:String)

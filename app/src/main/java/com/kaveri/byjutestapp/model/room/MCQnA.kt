package com.kaveri.byjutestapp.model.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MC QnA")
open class MCQnA(@PrimaryKey @ColumnInfo(name="id") @NonNull val id:String, val qno:Int, val qStr: String, val ans:String)

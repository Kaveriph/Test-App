package com.kaveri.byjutestapp.model.room

import androidx.room.Entity

@Entity(tableName = "SA QnA")
class SAQnA(id: String, qno:Int, qStr: String, ans:String, val imgLoc:String) : MCQnA(id, qno, qStr, ans)

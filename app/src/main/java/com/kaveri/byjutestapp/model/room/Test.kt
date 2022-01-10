package com.kaveri.byjutestapp.model.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Test")
class Test(@PrimaryKey @ColumnInfo(name="assessmentId") @NonNull val assessmentId: String,
           val assessmentName: String? = null,
           val subject: String? = null,
           val duration: Int? = null,
           val questions: String? = null,
           val totalMarks: Int? = null
)

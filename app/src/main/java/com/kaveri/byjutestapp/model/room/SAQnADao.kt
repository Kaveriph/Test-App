package com.kaveri.byjutestapp.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SAQnADao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(saQnA: SAQnA)

    @Query("SELECT * FROM `sa qna`")
    suspend fun getSAQnAData(): List<SAQnA>

    @Query("DELETE FROM `sa qna`")
    suspend fun deleteSaQnAData()
}
package com.kaveri.byjutestapp.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(test: Test)

    @Query("SELECT * FROM Test")
    suspend fun getTestData(): Test

    @Query("DELETE FROM Test")
    suspend fun deleteTestData()
}
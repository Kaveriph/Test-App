package com.kaveri.byjutestapp.model.room

import androidx.room.*

@Dao
interface MCQnADao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mcQnA: MCQnA)

    @Query("SELECT * FROM `mc qna`")
    suspend fun getMCQnAData(): List<MCQnA>

    @Query("DELETE FROM `MC QnA`")
    suspend fun deleteMcQnaData()
}
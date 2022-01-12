package com.kaveri.byjutestapp.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnswersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(answers: Answers)

    @Query("SELECT * FROM Answers ORDER BY qno ASC")
    suspend fun getAnswers(): List<Answers>

    @Query("DELETE FROM Answers")
    suspend fun deleteAnswers()
}
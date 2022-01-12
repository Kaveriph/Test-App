package com.kaveri.byjutestapp.model.repository

import com.kaveri.byjutestapp.model.room.MCQnA
import com.kaveri.byjutestapp.model.room.Answers
import com.kaveri.byjutestapp.model.room.Test

interface IRoomDbRepository {

    //Test Questions data
    suspend fun insert(test:Test)
    suspend fun getTestData(): Test
    suspend fun deleteTestDataFromDb()

    //Essay type questions data
    suspend fun insertAnswersIntoDb(answers: Answers)
    suspend fun getAnswersFromDb(): List<Answers>
    suspend fun deleteAnswersFromDb()
}
package com.kaveri.byjutestapp.model.repository

import com.kaveri.byjutestapp.model.room.MCQnA
import com.kaveri.byjutestapp.model.room.SAQnA
import com.kaveri.byjutestapp.model.room.Test

interface IRoomDbRepository {

    //Test Questions data
    suspend fun insert(test:Test)
    suspend fun getTestData(): Test
    suspend fun deleteTestDataFromDb()

    // Mulitple choice questions data
    suspend fun insertMCQnaIntoDb(mcQnA: MCQnA)
    suspend fun getMCQnADataFromDb(): List<MCQnA>
    suspend fun deleteMCQnAFromDb()

    //Essay type questions data
    suspend fun insertSAQnAIntoDb(saQnA: SAQnA)
    suspend fun getSAQnADataFromDb(): List<SAQnA>
    suspend fun deleteSAQnAFromDb()
}
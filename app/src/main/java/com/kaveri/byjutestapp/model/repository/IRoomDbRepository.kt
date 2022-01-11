package com.kaveri.byjutestapp.model.repository

import com.kaveri.byjutestapp.model.room.Test

interface IRoomDbRepository {
    suspend fun insert(test:Test)
    suspend fun getTestData(): Test
    suspend fun deleteTestDataFromDb()
}
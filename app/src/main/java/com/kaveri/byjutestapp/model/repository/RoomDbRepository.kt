package com.kaveri.byjutestapp.model.repository

import com.kaveri.byjutestapp.model.room.Test
import com.kaveri.byjutestapp.model.room.TestDao

class RoomDbRepository(private val testDao: TestDao) : IRoomDbRepository {

    override suspend fun insert(test: Test) {
        testDao.insert(test)
    }

    override suspend fun getTestData(): Test {
        return testDao.getTestData()
    }

    override suspend fun deleteTestDataFromDb() {
        testDao.deleteTestData()
    }
}
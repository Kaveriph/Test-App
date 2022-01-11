package com.kaveri.byjutestapp.model.repository

import com.kaveri.byjutestapp.model.room.*

class RoomDbRepository(private val testDao: TestDao, private val mcQnADao: MCQnADao, private val saQnADao: SAQnADao) : IRoomDbRepository {

    override suspend fun insert(test: Test) {
        testDao.insert(test)
    }

    override suspend fun getTestData(): Test {
        return testDao.getTestData()
    }

    override suspend fun deleteTestDataFromDb() {
        testDao.deleteTestData()
    }

    override suspend fun insertMCQnaIntoDb(mcQnA: MCQnA) {
        mcQnADao.insert(mcQnA)
    }

    override suspend fun getMCQnADataFromDb(): List<MCQnA> {
        return mcQnADao.getMCQnAData()
    }

    override suspend fun deleteMCQnAFromDb() {
        mcQnADao.deleteMcQnaData()
    }

    override suspend fun insertSAQnAIntoDb(saQnA: SAQnA) {
        saQnADao.insert(saQnA)
    }

    override suspend fun getSAQnADataFromDb(): List<SAQnA> {
        return saQnADao.getSAQnAData()
    }

    override suspend fun deleteSAQnAFromDb() {
        saQnADao.deleteSaQnAData()
    }
}
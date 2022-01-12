package com.kaveri.byjutestapp.model.repository

import com.kaveri.byjutestapp.model.room.*

class RoomDbRepository(private val testDao: TestDao, private val answersDao: AnswersDao) : IRoomDbRepository {

    override suspend fun insert(test: Test) {
        testDao.insert(test)
    }

    override suspend fun getTestData(): Test {
        return testDao.getTestData()
    }

    override suspend fun deleteTestDataFromDb() {
        testDao.deleteTestData()
    }

    override suspend fun insertAnswersIntoDb(answers: Answers) {
        answersDao.insert(answers)
    }

    override suspend fun getAnswersFromDb(): List<Answers> {
        return answersDao.getAnswers()
    }

    override suspend fun deleteAnswersFromDb() {
        answersDao.deleteAnswers()
    }
}
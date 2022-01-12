package com.kaveri.byjutestapp.model.repository

import android.content.Context
import com.kaveri.byjutestapp.model.dataobject.Test
import com.kaveri.byjutestapp.model.room.MCQnA
import com.kaveri.byjutestapp.model.room.Answers
import com.kaveri.byjutestapp.model.room.TestRoomDatabase

/**
 * This class implements all the methods to access the Model
 * */
class TestRepository(val context: Context) : ISharedPrefRepository, INetworkRepository, IRoomDbRepository {

    private val networkRepository by lazy {
        NetworkRepository()
    }
    private val sharedPrefRepository by lazy {
        SharedPreferenceRepository()
    }
    private val database by lazy {
        TestRoomDatabase.getDatabase(context)
    }
    private val dbRepository by lazy {
        RoomDbRepository(database.testDao(), database.andswersDao())
    }

    /**
     * This method returns the Test end time set in the Shared preference
     * It returns -1L if the data is not set in the sharedpreference
     *
     * */
    override fun getTestEndTime(context: Context): Long {
        return sharedPrefRepository.getTestEndTime(context = context)
    }

    /**
     * This method sets the End time in the Shared preference
     * @param context is required to get the access to the application's shared preference file
     * @param testTimeInMinutes is the total duration of the test in minutes
     * */
    override fun setEndTime(context: Context, testTimeInMinutes: Int) {
        sharedPrefRepository.setTestEndTime(context, testTimeInMinutes)
    }


    /**
     * This method clears the Test end time saved in the shared preference
     * @param context is required to get access to the ap's shared preference file
     *
     * */
    override fun clearSharedPrefData(context: Context) {
        sharedPrefRepository.clearTestInfo(context)
    }

    /***
     * This method reads the Test data from the backend
     * */
    override fun getTestData(
        successCallback: (test: Test?) -> Unit,
        failureCallback: (errorMessage: String) -> Unit
    ) {
        networkRepository.getTestData(successCallback, failureCallback)
    }

    /**
     * This method inserts the data into the Databse
     *  */
    override suspend fun insert(test: com.kaveri.byjutestapp.model.room.Test) {
        dbRepository.insert(test)
    }

    /**
     * This method reads the TestEntity data from the database.
     * */
    override suspend fun getTestData(): com.kaveri.byjutestapp.model.room.Test {
        return dbRepository.getTestData()
    }


    /**
     * This method calls the method from dbRepository to delete all the data in the database
     * This needs to be called when the test is submitted.
     * */
    override suspend fun deleteTestDataFromDb() {
        return dbRepository.deleteTestDataFromDb()
    }

    override suspend fun insertAnswersIntoDb(answers: Answers) {
        dbRepository.insertAnswersIntoDb(answers = answers)
    }

    override suspend fun getAnswersFromDb(): List<Answers> {
        return dbRepository.getAnswersFromDb()
    }

    override suspend fun deleteAnswersFromDb() {
        dbRepository.deleteAnswersFromDb()
    }
}
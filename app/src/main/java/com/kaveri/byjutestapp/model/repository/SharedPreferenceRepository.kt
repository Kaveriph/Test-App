package com.kaveri.byjutestapp.model.repository

import android.content.Context
import android.content.SharedPreferences

/**
* This is a helper for TestRepository to read the data from SharedPreefrences
*
* */
class SharedPreferenceRepository {
    private val TEST_SUBMITTED = "Test_submitted"
    private val sharedPrefFile = "ByjusSharedPreference"
    private val TEST_END_TIME = "test_end_time"
    /**
    * getting the shared preference object
    * */
    private fun getSharedPreference(context: Context) : SharedPreferences {
        return context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
    }

    /**
    * This method checks for the TestEndTime set in the app.
    * If the END time is not set, it will return -1 meaning the test is not yet started
    * */
    fun getTestEndTime(context: Context) : Long {
        return getSharedPreference(context).getLong(TEST_END_TIME, -1L)
    }

    /**
    * This method sets the start time
    *
    * @param context -> to create a sharedpreference
    * @param minutes -> is the duration of the test.
    * */
    fun setTestEndTime(context: Context, minutes: Int) {
        val starttime = System.currentTimeMillis()
        val endTime = System.currentTimeMillis() + (minutes * 60 * 1000)
        println("start time : $starttime, end time: $endTime")
        getSharedPreference(context)
            .edit()
            .clear()
            .putLong(TEST_END_TIME, endTime)
            .apply()
    }

    /**
    * This method clears the shared preference info
    * This method should be triggered only when the test is complete
    *
    * */
    fun clearTestInfo(context: Context) {
        getSharedPreference(context).edit().clear().apply()
    }


    fun submitTest(context: Context) {
        getSharedPreference(context).edit().
                putBoolean(TEST_SUBMITTED, true)
            .apply()
    }

    fun getTestSubmitted(context: Context) : Boolean {
        return getSharedPreference(context).getBoolean(TEST_SUBMITTED, false)
    }
}
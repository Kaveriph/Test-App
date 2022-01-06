package com.kaveri.byjutestapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceHelper {
    private val sharedPrefFile = "ByjusSharedPreference"
    private val TEST_END_TIME = "test_end_time"

    /*
    * getting the shared prefernce object
    * */
    private fun getSharedPreference(context: Context) : SharedPreferences {
        return context.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
    }

    /*
    * This method checs for the TestEndTime set in the app.
    * If the END time is not set, it will return -1 meaning the test is not yet started
    * */
    fun getTestEndTime(context: Context) : Long {
        return getSharedPreference(context).getLong(TEST_END_TIME, -1L)
    }

    /*
    * This method sets the start time
    *
    * @param context -> to create a sharedprefernce
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

    fun clearTestInfo(context: Context) {
        getSharedPreference(context).edit().clear().apply()
    }
}
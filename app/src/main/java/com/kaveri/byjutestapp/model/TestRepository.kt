package com.kaveri.byjutestapp.model

import android.content.Context
/**
* This class implements all the methods to access the Model
* */
class TestRepository : ISharedPrefRepository {


    /**
    * This method returns the Test end time set in the Shared preference
    * It returns -1L if the data is not set in the sharedpreference
    *
    * */
    override fun getTestEndTime(context: Context) : Long{
        return SharedPreferenceRepository().getTestEndTime(context = context)
    }

    /**
     * This method sets the End time in the Shared preference
     * @param context is required to get the access to the application's shared preference file
     * @param testTimeInMinutes is the total duration of the test in minutes
     * */
    override fun setEndTime(context: Context, testTimeInMinutes: Int) {
        SharedPreferenceRepository().setTestEndTime(context, testTimeInMinutes)
    }
}
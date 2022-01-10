package com.kaveri.byjutestapp.model.repository

import android.content.Context
import com.kaveri.byjutestapp.model.dataobject.Test

/**
 * This class implements all the methods to access the Model
 * */
class TestRepository : ISharedPrefRepository, INetworkRepository {

    private val networkRepository by lazy {
        NetworkRepository()
    }
    private val sharedPrefRepository by lazy {
        SharedPreferenceRepository()
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
        SharedPreferenceRepository().setTestEndTime(context, testTimeInMinutes)
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
}
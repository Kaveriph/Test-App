package com.kaveri.byjutestapp.model

import android.content.Context

/**
* This interface defines the methods to access the sharedpreference
*
* */
interface ISharedPrefRepository {

    fun getTestEndTime(context: Context) : Long
    fun setEndTime(context: Context, testTime: Int)
}
package com.kaveri.byjutestapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kaveri.byjutestapp.model.dataobject.Test
import com.kaveri.byjutestapp.model.repository.TestRepository

class TestViewModel(application: Application) : AndroidViewModel(application) {

    var testEndTime : MutableLiveData<Long> = MutableLiveData()
    private val mTestRepository: TestRepository = TestRepository()
    var testData: MutableLiveData<Test> = MutableLiveData()

    init {
        testEndTime.value = mTestRepository.getTestEndTime(getApplication())
    }

    fun setEndTime(testTime: Int) {
        mTestRepository.setEndTime(getApplication(), testTime)
        testEndTime.postValue(mTestRepository.getTestEndTime(getApplication()))
    }

    /**
     * This is the method to read the TEstData from the backend
     *
     * */
    fun getTestData() {
        mTestRepository.getTestData(
            successCallback = {
                testData.postValue(it)
            }, failureCallback = {
                Toast.makeText(getApplication(), it, Toast.LENGTH_SHORT).show()
            }
        )
    }

}

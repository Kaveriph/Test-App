package com.kaveri.byjutestapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kaveri.byjutestapp.model.TestRepository
import com.kaveri.byjutestapp.model.SharedPreferenceRepository

class TestViewModel(application: Application) : AndroidViewModel(application) {

    var testEndTime : MutableLiveData<Long> = MutableLiveData()
    private val mTestRepository: TestRepository = TestRepository()

    init {
        testEndTime.value = mTestRepository.getTestEndTime(getApplication())
    }

    fun setEndTime(testTime: Int) {
        mTestRepository.setEndTime(getApplication(), testTime)
        testEndTime.value = mTestRepository.getTestEndTime(getApplication())
    }

}

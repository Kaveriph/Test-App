package com.kaveri.byjutestapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.kaveri.byjutestapp.model.dataobject.Test
import com.kaveri.byjutestapp.model.repository.TestRepository
import kotlinx.coroutines.launch

class TestViewModel(application: Application) : AndroidViewModel(application) {

    var testEndTime : MutableLiveData<Long> = MutableLiveData()
    private val mTestRepository: TestRepository = TestRepository(context = application)
    var testData: MutableLiveData<Test> = MutableLiveData()

    init {
        testEndTime.value = mTestRepository.getTestEndTime(getApplication())
    }

    private fun setEndTime(testTime: Int) {
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
                it?.let {
                    storeTestDataInDb(it)
                    testData.postValue(it)
                    it.duration?.let { duration ->
                        setEndTime(duration)
                    }
                }
            }, failureCallback = {
                Toast.makeText(getApplication(), it, Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun storeTestDataInDb(test: Test) {
            val questionStr = Gson().toJson(test.questions).toString()
            val testEntity: com.kaveri.byjutestapp.model.room.Test =
                com.kaveri.byjutestapp.model.room.Test(test.assessmentId ?:"", test.assessmentName, test.subject,
                test.duration, questionStr, test.totalMarks)
            viewModelScope.launch {
                mTestRepository.insert(testEntity)
            }
    }

}

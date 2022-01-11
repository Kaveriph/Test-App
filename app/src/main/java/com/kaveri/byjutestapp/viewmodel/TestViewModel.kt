package com.kaveri.byjutestapp.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.kaveri.byjutestapp.model.dataobject.Questions
import com.kaveri.byjutestapp.model.dataobject.Test
import com.kaveri.byjutestapp.model.repository.TestRepository
import com.kaveri.byjutestapp.model.room.MCQnA
import com.kaveri.byjutestapp.model.room.SAQnA
import kotlinx.coroutines.launch
import org.json.JSONArray

class TestViewModel(application: Application) : AndroidViewModel(application) {

    var mcQna = MutableLiveData<List<MCQnA>>()
    var saQna = MutableLiveData<List<SAQnA>>()
    var testEndTime: MutableLiveData<Long> = MutableLiveData()
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
    fun getTestDataFromBackEnd() {
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
            com.kaveri.byjutestapp.model.room.Test(
                test.assessmentId ?: "", test.assessmentName, test.subject,
                test.duration, questionStr, test.totalMarks
            )
        viewModelScope.launch {
            mTestRepository.insert(testEntity)
        }
    }


    /**
     * This method reads the test data from the DB
     * */
    fun readTestDataFromDb() {
        viewModelScope.launch {
            val testEntity = mTestRepository.getTestData()
            if (testEntity != null) {
                val questionsJsonArray = Gson().fromJson<JsonArray>(
                    testEntity.questions,
                    JsonArray::class.java
                )
                val quest = ArrayList<Questions>()
                for (question in questionsJsonArray) {
                    quest.add(Gson().fromJson<Questions>(question, Questions::class.java))
                }
                val testObj = Test(
                    assessmentId = testEntity.assessmentId,
                    assessmentName = testEntity.assessmentName,
                    subject = testEntity.subject,
                    duration = testEntity.duration,
                    questions = quest,
                    totalMarks = testEntity.totalMarks
                )
                testData.postValue(testObj)
            }
        }
    }

    /**
     * This method calls the delete method for the
     * TestEntity to be deleted from the database
     */
    fun deleteTestData(context: Context) {
        viewModelScope.launch {
            mTestRepository.deleteTestDataFromDb()
            mTestRepository.clearSharedPrefData(context)
        }
    }

    /**
     * This method inserts the multiple choice questions in the database
     */
    fun saveMCQNA(mcQnA: MCQnA) {
        viewModelScope.launch {
            mTestRepository.insertMCQnaIntoDb(mcQnA)
        }
    }

    /**
     * This method inserts the SA question and answers in the database.
     */
    fun saveSAQna(saQnA: SAQnA) {
        viewModelScope.launch {
            mTestRepository.insertSAQnAIntoDb(saQnA)
        }
    }

    /**
     * this method reads the MCQnA stored in the database
     * */
    fun readMCQNAFromDb()  {
        viewModelScope.launch {
            mcQna.postValue(mTestRepository.getMCQnADataFromDb())
        }
    }

    /**
    * This method reads the Essay Qns and Answers from the database
    * */
    fun readSAQNAFromDb()  {
        viewModelScope.launch {
            mcQna.postValue(mTestRepository.getSAQnADataFromDb())
        }
    }
}

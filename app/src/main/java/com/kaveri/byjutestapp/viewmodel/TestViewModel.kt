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
import com.kaveri.byjutestapp.model.room.Answers
import kotlinx.coroutines.*

class TestViewModel(application: Application) : AndroidViewModel(application) {

    var answers = MutableLiveData<List<Answers>>()
    var testEndTime: MutableLiveData<Long> = MutableLiveData()
    var testSubmitted: MutableLiveData<Boolean> = MutableLiveData()
    private val mTestRepository: TestRepository = TestRepository(context = application)
    var testData: MutableLiveData<Test> = MutableLiveData()
    var mTextToDisplay: MutableLiveData<String> = MutableLiveData()

    init {
        testSubmitted.value = mTestRepository.getTestSubmitted(getApplication())
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
        storeDefaultAnswersInDb(test)
    }

    private fun storeDefaultAnswersInDb(test: Test) {
        for(question in test.questions) {

            val answers =
                if(question.type.equals("MC"))
                        Answers(question.id ?: "", question.type ?: "", question.qno ?: -1, question.text ?: "", "Not Applicable", "Not applicable")
                else Answers(question.id ?: "", question.type ?: "", question.qno ?: -1, question.text ?: "", "Not Applicable", "")

            saveAnswersInDb(answers)
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
    * This method reads the Essay Qns and Answers from the database
    * */
    fun readAnswersFromDb()  {
        viewModelScope.launch {
            answers.postValue(mTestRepository.getAnswersFromDb())
        }
    }

    fun saveAnswersInDb(answers: Answers) {
        viewModelScope.launch {
            mTestRepository.insertAnswersIntoDb(answers = answers)
        }
    }

    fun deleteAnswersInDb() {
        viewModelScope.launch {
            mTestRepository.deleteAnswersFromDb()
        }
    }

    fun submitTest(context: Context) {
        mTestRepository.submitTest(context = context)
        testSubmitted.postValue(true)
    }

    fun getTestSubmitted(context: Context) : Boolean {
        return mTestRepository.getTestSubmitted(context)
    }


    /**
     * This method runa a coroutine to update the timer for the user.
     */
    fun updateTimer() {
        testEndTime.value?.let {
            val endTime = it
            viewModelScope.launch {
                val currentTimeInMillis = System.currentTimeMillis()
                println("time: $endTime  -------  $currentTimeInMillis")
                var secondsLeft = (endTime / 1000) - currentTimeInMillis / 1000
                val endTimeInMin = (endTime / 60000)
                val currentTimeInMin = currentTimeInMillis / 60000
                var minLeft = endTimeInMin - currentTimeInMin
                println("min left $minLeft : $secondsLeft")
                while (minLeft > 1) {
                    val texttoDisplay = "$minLeft min"
                    println("min left $minLeft")
                    mTextToDisplay.postValue(texttoDisplay)
                    delay(secondsLeft)
                    minLeft--
                    secondsLeft = 60000
                    println("min left $minLeft : $secondsLeft")
                }
                var seconds = 60
                while (seconds > 1) {
                    val textToDisplay = "$seconds sec"
                    mTextToDisplay.postValue(textToDisplay)
                    delay(1000)
                    seconds--
                    println("min left $minLeft : $secondsLeft")
                }
                withContext(Dispatchers.Main) {
                    submitTest(getApplication())
                }
            }
        }
    }
}

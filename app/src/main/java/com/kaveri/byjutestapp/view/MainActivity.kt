package com.kaveri.byjutestapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kaveri.byjutestapp.R
import com.kaveri.byjutestapp.utils.SharedPreferenceHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
        initListeners()
    }

    private fun initListeners() {
        btnStartTest.setOnClickListener {
            retrieveQuestionPaper()
        }
    }

    private fun retrieveQuestionPaper() {
        println("Call VM to retrieve Question paper...")
    }

    private fun initUi() {
        val testEndTime = SharedPreferenceHelper().getTestEndTime(this)
        if(testEndTime == -1L) {
            btnStartTest.visibility = View.VISIBLE
        } else {
            btnStartTest.visibility = View.GONE
            handleTestStatus(testEndTime)
        }
    }

    private fun handleTestStatus(testEndTime: Long) {
        val currentTime = System.currentTimeMillis()
        if(testEndTime > currentTime) {
            loadTheSavedTest()
        } else {
            btnStartTest.visibility = View.VISIBLE
            SharedPreferenceHelper().clearTestInfo(this)
            println("Start Test again")
        }
    }

    private fun loadTheSavedTest() {
        println("Loading from DB.....")
    }
}
package com.kaveri.byjutestapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kaveri.byjutestapp.utils.SharedPreferenceHelper

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class TestFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false)
    }


    private fun init() {
        val testEndTime = context?.let { SharedPreferenceHelper().getTestEndTime(it) }
        testEndTime?.let { handleTestStatus(it) }
    }

    private fun handleTestStatus(testEndTime: Long) {
        val currentTime = System.currentTimeMillis()
        if(testEndTime > currentTime) {
            loadTheSavedTest()
        } else {
            context?.let { SharedPreferenceHelper().clearTestInfo(it) }
            println("Start Test again")
        }
    }

    private fun loadTheSavedTest() {
        println("Loading from DB.....")
    }

    private fun retrieveQuestionPaper() {
        println("Call VM to retrieve Question paper...")
    }
}
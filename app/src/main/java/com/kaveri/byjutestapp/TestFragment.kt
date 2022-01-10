package com.kaveri.byjutestapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import com.kaveri.byjutestapp.model.repository.SharedPreferenceRepository
import com.kaveri.byjutestapp.viewmodel.TestViewModel
import java.util.*

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class TestFragment : Fragment() {

    private val mViewModel: TestViewModel by lazy {
        ViewModelProvider(this).get(TestViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            activity?.finishAndRemoveTask()
        }
    }

    private fun initObservers() {
        mViewModel.testEndTime.observe(viewLifecycleOwner,  {
            println("TestFrag Test end time is ${Date(it)}")
            handleTestStatus(it)
        })
        mViewModel.testData.observe(viewLifecycleOwner, {
            println("Test data retrieved ${it.toString()}")
        })
    }


    private fun handleTestStatus(testEndTime: Long) {
        val currentTime = System.currentTimeMillis()
        if (testEndTime == -1L) {
            retrieveQuestionPaper()
        } else if(testEndTime > currentTime) {
            loadTheSavedTest()
        } else {
            context?.let { SharedPreferenceRepository().clearTestInfo(it) }
            println("Start Test again")
        }
    }

    private fun loadTheSavedTest() {
        println("Loading from DB.....")
    }

    private fun retrieveQuestionPaper() {
        println("Call VM to retrieve Question paper...")
        mViewModel.getTestData()
        startTimer()
    }


    private fun startTimer() {
        mViewModel.setEndTime(30)
    }
}
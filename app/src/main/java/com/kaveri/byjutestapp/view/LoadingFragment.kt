package com.kaveri.byjutestapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kaveri.byjutestapp.R
import com.kaveri.byjutestapp.viewmodel.TestViewModel
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class LoadingFragment : Fragment() {

    private val mViewModel by lazy {
        ViewModelProvider(this).get(TestViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initUi()
    }

    private fun initObservers() {
        mViewModel.testEndTime.observe(viewLifecycleOwner, {
            println("LoadingFrag Test end time is ${Date(it)}")
            handleTestStatus(it, mViewModel.testSubmitted.value ?: false)
        })
        mViewModel.testSubmitted.observe(viewLifecycleOwner, {
            handleTestStatus(mViewModel.testEndTime.value ?: -1L, it)
        })
    }
    private fun handleTestStatus(testEndTime: Long, testSubmitted: Boolean) {
        val currentTime = System.currentTimeMillis()
        if(testEndTime == -1L) {
            findNavController().navigate(R.id.action_loadingFragment_to_startTestFragment)
        } else if(testEndTime!! > currentTime && !testSubmitted) {
            findNavController().navigate(R.id.action_loadingFragment_to_testFragment)
        } else {
            // when the test time is over, deleteTestData()
            // Or
            findNavController().navigate(R.id.action_loadingFragment_to_testSubmitFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    private fun initUi() {
        val testEndTime = mViewModel.testEndTime
        val testSubmitted = mViewModel.testSubmitted.value ?: false
        val currentTime = System.currentTimeMillis()
        if(testEndTime.value == -1L) {
            findNavController().navigate(R.id.action_loadingFragment_to_startTestFragment)
        } else if(testEndTime.value!! > currentTime && !testSubmitted) {
            findNavController().navigate(R.id.action_loadingFragment_to_testFragment)
        } else {
            // when the test time is over, deleteTestData()
            // Or
            findNavController().navigate(R.id.action_loadingFragment_to_testSubmitFragment)
        }
    }

}
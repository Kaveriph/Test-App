package com.kaveri.byjutestapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kaveri.byjutestapp.R
import com.kaveri.byjutestapp.viewmodel.TestViewModel
import kotlinx.android.synthetic.main.fragment_start_test.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class StartTestFragment : Fragment() {

    private val mViewModel by lazy {
        ViewModelProvider(this).get(TestViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        mViewModel.testEndTime.observe(viewLifecycleOwner,  {
            println("StartFrag : Test end time is ${Date(it)}")
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_test, container, false)
    }


    private fun initListeners() {
        btnStartTest.setOnClickListener {
            findNavController().navigate(R.id.action_startTestFragment_to_testFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            activity?.finishAndRemoveTask()
        }
    }

}
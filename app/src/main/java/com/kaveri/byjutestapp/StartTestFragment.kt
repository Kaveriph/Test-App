package com.kaveri.byjutestapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_start_test.*


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class StartTestFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
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
package com.kaveri.byjutestapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.kaveri.byjutestapp.model.SharedPreferenceRepository
import com.kaveri.byjutestapp.viewmodel.TestViewModel

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
        initUi()
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
        if(testEndTime.value == -1L) {
            findNavController().navigate(R.id.action_loadingFragment_to_startTestFragment)
        } else {
            findNavController().navigate(R.id.action_loadingFragment_to_testFragment)
        }
    }

}
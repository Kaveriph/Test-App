package com.kaveri.byjutestapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaveri.byjutestapp.R
import com.kaveri.byjutestapp.model.room.Answers
import com.kaveri.byjutestapp.viewmodel.TestViewModel
import kotlinx.android.synthetic.main.fragment_test_submit.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TestSubmitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestSubmitFragment : Fragment() {
    private lateinit var recyclerViewAdapter: SubmittedRecyclerView
    private var answersList = ArrayList<Answers>()
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
        return inflater.inflate(R.layout.fragment_test_submit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObservers()
    }

    private fun initUi() {
        recyclerViewAdapter = SubmittedRecyclerView(answerList = answersList, context = requireContext())
        recyclerView.adapter = recyclerViewAdapter
        recyclerView.setLayoutManager(LinearLayoutManager(requireContext()));
        mViewModel.readAnswersFromDb()
    }

    private fun initObservers() {
        mViewModel.answers.observe(viewLifecycleOwner, {
            answersList.addAll(it)
            recyclerViewAdapter.notifyDataSetChanged()
        })
    }

}
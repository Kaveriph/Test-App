package com.kaveri.byjutestapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.kaveri.byjutestapp.R
import com.kaveri.byjutestapp.model.dataobject.Questions
import com.kaveri.byjutestapp.model.room.Answers
import com.kaveri.byjutestapp.viewmodel.TestViewModel
import kotlinx.android.synthetic.main.fragment_test.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class TestFragment : Fragment() {

    private lateinit var viewPager2Adapter: ViewPager2Adapter
    var listOfQuestions = ArrayList<Questions>()
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
        initView()
        initListeners()
        initObservers()
    }

    private fun initView() {
        viewPager2Adapter = ViewPager2Adapter(
            requireContext(),
            listOfQuestions,
            answerSelectedCallback = { answers ->
                kotlin.run {
                    saveAnswers(answers)
                }
            })
        testViewPager.adapter = viewPager2Adapter
        testViewPager.registerOnPageChangeCallback(ViewPageCallback())
    }

    private fun saveAnswers(ans: Answers) {
        println("QNA: ${ans.ans}")
        mViewModel.saveAnswersInDb(ans)
    }

    class ViewPageCallback : ViewPager2.OnPageChangeCallback() {

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
        }
    }

    private fun initListeners() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            activity?.finishAndRemoveTask()
        }
        btnSubmit.setOnClickListener {
            submitTest()
        }
    }

    private fun initObservers() {
        mViewModel.mTextToDisplay.observe(viewLifecycleOwner, {
            if(tvTimer != null) tvTimer.text = it
        })
        mViewModel.testEndTime.observe(viewLifecycleOwner, {
            println("TestFrag Test end time is ${Date(it)}")
            handleTestStatus(it, mViewModel.testSubmitted.value ?: false)
        })
        mViewModel.testSubmitted.observe(viewLifecycleOwner, {
            handleTestStatus(mViewModel.testEndTime.value ?: -1L, it)
        })
        mViewModel.testData.observe(viewLifecycleOwner, {
            println("Test data retrieved ${it.toString()}")
            listOfQuestions.addAll(it.questions)
            viewPager2Adapter.notifyDataSetChanged()
        })
        mViewModel.answers.observe(viewLifecycleOwner, {
            println("QNA read for SA Type")
            //set the same data on UI
        })
    }


    private fun handleTestStatus(testEndTime: Long, testSubmitted: Boolean) {
        val currentTime = System.currentTimeMillis()
        if (testEndTime == -1L) {
            retrieveQuestionPaper()
        } else if (testEndTime > currentTime && !testSubmitted) {
            loadTheSavedTest()
            mViewModel.updateTimer()
          /*  updateTimer(testEndTime, { textToDisplay ->
                run {
                    if(tvTimer != null) tvTimer.text = textToDisplay
                }
            })*/
        } else {
            // when the test time is over, deleteTestData()
            // Or
            submitTest()
        }
    }

    private fun submitTest() {
        findNavController().navigate(R.id.action_testFragment_to_testSubmitFragment)
    }

    private fun updateTimer(testEndTime: Long, callback: (String) -> Unit) {

        CoroutineScope(Dispatchers.Default).launch {
            val endTimeInMin = testEndTime / 60000
            val currentTimeInMin = System.currentTimeMillis() / 60000
            var minLeft = endTimeInMin - currentTimeInMin
            while (minLeft > 1) {
                withContext(Dispatchers.Main) {
                    val texttoDisplay = "$minLeft min"
                    callback.invoke(texttoDisplay)
                }
                delay(60000)
                minLeft--
            }
            var seconds = 60
            while (seconds > 1) {
                withContext(Dispatchers.Main) {
                    val textToDisplay = "$seconds sec"
                    callback.invoke(textToDisplay)
                }
                delay(1000)
                seconds--
            }
            withContext(Dispatchers.Main) {
                submitTest()
            }
        }
    }

    private fun loadTheSavedTest() {
        mViewModel.readTestDataFromDb()
    }

    private fun retrieveQuestionPaper() {
        mViewModel.getTestDataFromBackEnd()
    }

    private fun deleteTestData() {
        mViewModel.deleteTestData(requireContext())
    }

}
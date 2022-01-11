package com.kaveri.byjutestapp.view

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaveri.byjutestapp.R
import com.kaveri.byjutestapp.model.dataobject.Questions
import com.kaveri.byjutestapp.model.room.MCQnA
import com.kaveri.byjutestapp.model.room.SAQnA
import kotlinx.android.synthetic.main.mc_test_layout.view.*
import kotlinx.android.synthetic.main.mc_test_layout.view.tvQuestion
import kotlinx.android.synthetic.main.sa_test_layout.view.*

class ViewPager2Adapter(val context: Context, var listOfQuestions: ArrayList<Questions>, var mcQtnCallback: (MCQnA) -> Unit, var saQtnCallback: (SAQnA) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_MC = 1
        const val VIEW_TYPE_SA = 2
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return when (viewType) {
            VIEW_TYPE_MC -> {
                MCViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.mc_test_layout, parent, false)
                , mcQtnCallback
                )
            }
            else -> {
                SAViewHolder(
                    LayoutInflater.from(context).inflate(R.layout.sa_test_layout, parent, false), saQtnCallback
                )
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_MC -> {
                (holder as MCViewHolder).bind(listOfQuestions.get(position))
            }
            VIEW_TYPE_SA -> {
                (holder as SAViewHolder).bind(listOfQuestions.get(position))
            }
        }

    }

    override fun getItemCount(): Int {
        return listOfQuestions.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (listOfQuestions.get(position).type.equals("MC")) {
            VIEW_TYPE_MC
        } else {
            VIEW_TYPE_SA
        }
    }


    class MCViewHolder(itemView: View, mcQtnCallback: (MCQnA) -> Unit) : RecyclerView.ViewHolder(itemView) {
        var tvQuestion: TextView = itemView.tvQuestion
        var radioGroup: RadioGroup = itemView.answerGroup
        var option1: RadioButton = itemView.option1
        var option2: RadioButton = itemView.option2
        var option3: RadioButton = itemView.option3
        var option4: RadioButton = itemView.option4
        var mcQtnCallback = mcQtnCallback

        fun bind(question: Questions) {
            tvQuestion.text = Html.fromHtml(question.text)
            option1.text = Html.fromHtml(question.mcOptions.get(0))
            option2.text = Html.fromHtml(question.mcOptions.get(1))
            option3.text = Html.fromHtml(question.mcOptions.get(2))
            option4.text = Html.fromHtml(question.mcOptions.get(3))
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                val radioButton = itemView.findViewById<RadioButton>(checkedId)
                println("selected answer: ${radioButton.text}")
                mcQtnCallback.invoke(MCQnA(question.id ?: "", question.qno ?: -1, question.text ?: "", "${radioButton.text}"))
            }
        }

    }

    class SAViewHolder(itemView: View, saQtnCallback: (SAQnA) -> Unit) : RecyclerView.ViewHolder(itemView) {
        var tvQuestion: TextView = itemView.tvQuestion
        var etAnswer: EditText = itemView.etAnswer
        var saQtnCallback = saQtnCallback

        fun bind(question: Questions) {
            tvQuestion.text = Html.fromHtml(question.text)

            etAnswer.setOnFocusChangeListener(  { view: View, hasFocus: Boolean ->
                if(!hasFocus) {
                    saQtnCallback.invoke(SAQnA(question.id ?: "", question.qno ?: -1, question.text ?: "", "${etAnswer.text}", ""))
                }
            })
        }

    }
}
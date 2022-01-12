package com.kaveri.byjutestapp.view

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaveri.byjutestapp.R
import com.kaveri.byjutestapp.model.room.Answers
import kotlinx.android.synthetic.main.answers_layout.view.*
import kotlinx.android.synthetic.main.answers_layout.view.tvQuestion
import kotlinx.android.synthetic.main.mc_test_layout.view.*

class SubmittedRecyclerView(val context:Context, val answerList: ArrayList<Answers>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.answers_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.tvQno.text = Html.fromHtml("${answerList.get(position).qno}")
        holder.itemView.tvQuestion.text = Html.fromHtml("${answerList.get(position).qStr}")
        holder.itemView.tvAnswer.text = Html.fromHtml("${answerList.get(position).ans}")
    }

    override fun getItemCount(): Int {
        return answerList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvQno: TextView = itemView.tvQno
        val tvQuestion: TextView = itemView.tvQuestion
        val tvAnswer: TextView = itemView.tvAnswer
    }
}
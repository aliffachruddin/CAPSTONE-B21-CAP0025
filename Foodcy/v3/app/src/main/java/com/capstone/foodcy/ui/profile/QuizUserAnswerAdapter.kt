package com.capstone.foodcy.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.foodcy.data.database.room.RoomUserAnswer
import com.capstone.foodcy.data.entity.QuestionEntity
import com.capstone.foodcy.data.entity.UserAnswerFb
import com.capstone.foodcy.databinding.ItemUserAnswerBinding
import com.capstone.foodcy.ui.profile.QuizUserAnswerAdapter.*

class QuizUserAnswerAdapter (val list: List<RoomUserAnswer>, val lq: List<QuestionEntity>) :
    RecyclerView.Adapter<UserAnswerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAnswerViewHolder {
        val itemUserAnswerBinding = ItemUserAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserAnswerViewHolder(itemUserAnswerBinding, lq)
    }

    override fun onBindViewHolder(holder: UserAnswerViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size



    class UserAnswerViewHolder(private val binding: ItemUserAnswerBinding, private val lq: List<QuestionEntity>) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RoomUserAnswer) {
            with(binding) {
                val question = getQuestion(item.idQuestion)
                val answer = getAnswer(item.idQuestion, item.answer.toString())
                tvUserAnswer.text = "${question}: ${answer}"
            }
        }

        private fun getQuestion(qId: Int) : String {
            var question = ""
            for (q in lq) {
                if (q.qId == qId) {
                    question = q.question
                }
            }
            return question
        }

        private fun getAnswer(qId: Int, aId:String) : String {
            var answer = aId
            for (q in lq) {
                if (q.qId == qId) {
                    for (la in q.answer) {
                        if (la.aId.toString() == aId) {
                            if (la.answer != null) {
                                answer = la.answer.toString()
                            }
                        }
                    }
                }
            }

            return answer
        }
    }


}
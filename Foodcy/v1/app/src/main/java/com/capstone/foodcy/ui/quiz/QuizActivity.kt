package com.capstone.foodcy.ui.quiz

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.capstone.foodcy.R
import com.capstone.foodcy.data.database.room.RoomUserAnswer
import com.capstone.foodcy.data.entity.AnswerEntity
import com.capstone.foodcy.data.entity.QuestionEntity
import com.capstone.foodcy.data.entity.UserAnswer
import com.capstone.foodcy.databinding.ActivityQuizBinding
import com.capstone.foodcy.utils.`object`.Utils.hideSoftKeyBoard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class QuizActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityQuizBinding
    private lateinit var viewModel: QuizViewModel
    private lateinit var listQuestions: List<QuestionEntity>
    private var qId = 0
    private var nMin = 0
    private lateinit var question: QuestionEntity
    private val userAnswer = ArrayList<UserAnswer>()
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: DatabaseReference
    private var user : FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(QuizViewModel::class.java)
        listQuestions = viewModel.getAllQuestions()
        question = listQuestions[qId]

        binding.progressBar.max = listQuestions.size

        setQuestions()

        binding.btnSubmit.setOnClickListener(this)

    }

    private fun setQuestions() {
        with(binding) {
            tvQuestion.text = question.question
            Log.e("--------", "${question.qId}---------")
            binding.progressBar.progress = qId+1
            binding.tvProgress.text = resources.getString(R.string.progress_value, (qId+1), listQuestions.size)

            if (question.isCustom) {
                edAnswer.text = null
                edAnswer.visibility = View.VISIBLE
                rgOptions.visibility = View.GONE

            } else {
                edAnswer.visibility = View.GONE
                rgOptions.visibility = View.VISIBLE

                rgOptions.removeAllViews()

                for (op in question.answer) {
                    val rbOption = RadioButton(this@QuizActivity)
                    rbOption.text = op.answer.toString()
                    rbOption.id = op.aId
                    rbOption.setTextColor(Color.BLACK)
                    rgOptions.addView(rbOption)
                }

            }
        }
        qId++
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_submit -> {
                hideSoftKeyBoard(applicationContext, v)

                if (validate()) {
                    return
                }

                val idQuestion = question.qId
                addList(idQuestion)

                when {
                    qId < listQuestions.size -> {
                        if (qId == listQuestions.size-1) {
                            binding.btnSubmit.text = resources.getString(R.string.submit)
                        }
                        question = listQuestions[qId]
                        setQuestions()
                    }
                    else -> {
                        Log.e("submit", userAnswer.toString())
                        viewModel.getAllUserAnswer(application).observe(this, { ua ->
                            if (ua.isEmpty()) {
                                Log.e("add", ua.toString())
                                addUserAnswerRoom()
                            } else {
                                Log.e("update", ua.toString())
                                updateUserAnswerRoom()
                            }
                        })
                        showResult()
                    }
                }

            }
        }
    }

    private fun validate() : Boolean {
        var isEmpty = false
        if (question.isCustom) {
            if (binding.edAnswer.text.toString().trim().isEmpty()) {
                binding.edAnswer.error = "This field must be filled"
                binding.edAnswer.requestFocus()
                isEmpty = true
            }
        } else {
            val rb = binding.rgOptions.checkedRadioButtonId
            Log.e("rb", rb.toString())
            getRange()
            Log.e("n", nMin.toString())
            if (rb < nMin) {
                Toast.makeText(this, "Pick one of them", Toast.LENGTH_SHORT).show()
                isEmpty = true
            }
        }

        return isEmpty
    }

    private fun getRange() {
        val la : List<AnswerEntity> = question.answer
        nMin = la.first().aId
    }

    private fun addList(id: Int) {
        val answer = if (question.isCustom) {
            binding.edAnswer.text.toString()
        } else {
            binding.rgOptions.checkedRadioButtonId.toString()
        }

        val score = getScore(id, answer)
        userAnswer.add(UserAnswer(id, score, answer))
    }

    private fun getScore(id: Int, answer: String?): String {
        return if (question.isCustom) {
            var xMax = 0
            var xMin = 0
            if (question.qId == 3) {
                xMax = 1311
                xMin = 13
            } else {
                xMax = 120
                xMin = 2
            }

            val xStd : Float = (answer.toString().toFloat() - xMin) / (xMax - xMin)
            xStd.toString()
        } else {
            val idAnswerChosen = answer.toString().toInt()
            val listAnswer = viewModel.getListAnswer(id)
            viewModel.getAnswerScore(idAnswerChosen, listAnswer).toString()
        }
    }

    private fun addUserAnswerRoom() {
        val list: List<UserAnswer> = userAnswer
        val uid = viewModel.user?.uid.toString()
        for (l in list) {
            val itemUserAnswer = RoomUserAnswer(l.idQuestion, l.answer, l.score, uid)
            viewModel.insert(itemUserAnswer, application)
        }
    }

    private fun updateUserAnswerRoom() {
        val list: List<UserAnswer> = userAnswer
        val uid = viewModel.user?.uid.toString()
        for (l in list) {
            val itemUserAnswer = RoomUserAnswer(l.idQuestion, l.answer, l.score, uid)
            viewModel.update(itemUserAnswer, application)
        }
    }

    private fun showResult() {
        binding.cardView.visibility = View.GONE
        binding.btnSubmit.visibility = View.GONE
        binding.llProgressDetails.visibility = View.GONE
        binding.tvResult.visibility = View.VISIBLE

        removeRadioButton()

        val listScore = ArrayList<String>()
        val list : List<UserAnswer> = userAnswer
        for (l in list) {
            if (l.idQuestion!=1) {
                listScore.add(l.score)
            }
        }

        val intent = Intent(this, ResultActivity::class.java)
        intent.putStringArrayListExtra("EXTRA_SCORES", listScore)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Return to Profile Page")
        builder.setMessage("Are you sure you want to return to the profile page? Your answer will not be saved.")
        builder.setPositiveButton("Exit") { dialog, _ ->
            removeRadioButton()
            super.onBackPressed()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun removeRadioButton() {
        binding.rgOptions.removeAllViews()
    }
}




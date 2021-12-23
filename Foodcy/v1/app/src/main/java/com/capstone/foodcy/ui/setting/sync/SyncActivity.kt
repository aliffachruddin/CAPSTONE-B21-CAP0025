package com.capstone.foodcy.ui.setting.sync

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.capstone.foodcy.R
import com.capstone.foodcy.data.database.room.RoomUserAnswer
import com.capstone.foodcy.data.database.room.RoomUserCluster
import com.capstone.foodcy.data.database.room.RoomUserFavorit
import com.capstone.foodcy.data.firebase.FirebaseUserAnswer
import com.capstone.foodcy.data.firebase.FirebaseUserCluster
import com.capstone.foodcy.data.firebase.FirebaseUserFavorit
import com.capstone.foodcy.databinding.ActivitySyncBinding
import com.capstone.foodcy.ui.main.MainActivity
import com.google.firebase.database.*

class SyncActivity : AppCompatActivity() {
    private lateinit var ref: DatabaseReference
    private lateinit var viewModel : SyncViewModel
    private lateinit var binding : ActivitySyncBinding
    private lateinit var listAnswer : MutableList<RoomUserAnswer>
    private lateinit var listCluster : MutableList<RoomUserCluster>
    private lateinit var listFavorit : MutableList<RoomUserFavorit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySyncBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(SyncViewModel::class.java)

        listAnswer = mutableListOf()
        listCluster = mutableListOf()
        listFavorit = mutableListOf()

        showSynchronizedDialog()
    }

    fun showSynchronizedDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.sync_dialog, null)

        builder.setView(view)
        builder.setPositiveButton("Synchronize now") { p0,p1->
            readUserAnswerFromFirebase()
        }

        builder.setNegativeButton("Never") { p0,p1->
            val intent = Intent(this@SyncActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        val alert = builder.create()
        alert.show()
    }

    private fun readUserAnswerFromFirebase() {
        ref = FirebaseDatabase.getInstance().reference.child("user_answer")
        ref.orderByChild("uid")
            .equalTo(viewModel.user!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        binding.progressBar.visibility = View.VISIBLE

                        listAnswer.clear()

                        for (s in snapshot.children) {
                            val answer = s.getValue(FirebaseUserAnswer::class.java)
                            if (answer != null) {
                                val item = RoomUserAnswer(answer.idQuestion, answer.answer, answer.score, answer.uid)
                                listAnswer.add(item)
                            }
                        }

                        checkUserAnswer()

                    } else {
                        goToMain()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Error Read Data", "loadPost:onCancelled, ${error.toException()}")
                }

            })
    }

    private fun checkUserAnswer() {
        viewModel.getAllUserAnswer(application).observe(this@SyncActivity, { ua ->
            if (ua.isEmpty()) {
                addUserAnswer()
            } else {
                updateUserAnswer()
            }
        })
    }

    private fun addUserAnswer() {
        for (item in listAnswer) {
            Log.e("ini list answer", item.idQuestion.toString())
            viewModel.insertUserAnswer(item, application)
        }
        readUserClusterFromFirebase()
    }

    private fun updateUserAnswer() {
        for (item in listAnswer) {
            Log.e("ini list answer update", item.idQuestion.toString())
            viewModel.updateUserAnswer(item, application)
        }
       readUserClusterFromFirebase()
    }


    private fun readUserClusterFromFirebase() {
        ref = FirebaseDatabase.getInstance().reference.child("user_cluster")
        ref.orderByChild("uid")
            .equalTo(viewModel.user!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        listCluster.clear()

                        for (s in snapshot.children) {
                            val answer = s.getValue(FirebaseUserCluster::class.java)

                            if (answer != null) {
                                val item = RoomUserCluster(answer.uid, answer.cluster.toString())
                                listCluster.add(item)
                            }
                        }

                    }

                    checkUserCluster()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Error Read Data", "loadPost:onCancelled, ${error.toException()}")
                }

            })
    }

    private fun checkUserCluster() {
        viewModel.getAllUserCluster(application).observe(this@SyncActivity, { uc ->
            if (uc.isEmpty()) {
                addUserCluster()
            } else {
                updateUserCluster()
            }
        })
    }

    private fun addUserCluster() {
        for (item in listCluster) {
            Log.e("ini list cluster", item.cluster.toString())
            viewModel.insertUserCluster(item, application)
        }
        readUserFavoriteFromFirebase()
    }

    private fun updateUserCluster() {
        for (item in listCluster) {
            Log.e("ini list cluster update", item.cluster.toString())
            viewModel.updateUserCluster(item, application)
        }
        readUserFavoriteFromFirebase()
    }


    private fun readUserFavoriteFromFirebase() {
        ref = FirebaseDatabase.getInstance().reference.child("user_favorite")
        ref.orderByChild("uid")
            .equalTo(viewModel.user!!.uid)
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        listFavorit.clear()

                        for (s in snapshot.children) {
                            val answer = s.getValue(FirebaseUserFavorit::class.java)

                            if (answer != null) {
                                val item = RoomUserFavorit(answer.uid, answer.idfood)
                                listFavorit.add(item)
                            }
                        }
                        checkUserFavorit()

                    } else {
                        goToMain()
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Error Read Data", "loadPost:onCancelled, ${error.toException()}")
                }
            })
    }

    private fun checkUserFavorit() {
        viewModel.getAllUserFavorit(application).observe(this@SyncActivity, { uf ->
            checkIdFavorit()
        })
    }

    private fun checkIdFavorit() {
        for (item in listFavorit) {
            Log.e("observe duluu", item.idFood)
            viewModel.getFavUserById(application, item.idFood).observe(this, { uf->
                if (uf.isEmpty()) {
                    Log.e("ini insert fav id", item.idFood)
                    viewModel.insertUserFavorite(item, application)
                } else {
                    Log.e("ini update fav id", item.idFood)
                    viewModel.updateUserFavorite(item, application)
                }
            })
        }
        goToMain()
    }


    private fun addUserFavorit() {
        for (item in listFavorit) {
            Log.e("ini insert fav", item.idFood)
            viewModel.insertUserFavorite(item, application)
        }
        goToMain()
    }

    private fun updateUserFavorit() {
        for (item in listFavorit) {
            Log.e("ini update fav", item.idFood)
            viewModel.updateUserFavorite(item, application)
        }
        goToMain()
    }

    private fun goToMain() {
        binding.progressBar.visibility = View.GONE
        finish()
    }
}
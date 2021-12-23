package com.capstone.foodcy.ui.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.foodcy.R
import com.capstone.foodcy.data.database.room.RoomUserAnswer
import com.capstone.foodcy.data.database.room.RoomUserCluster
import com.capstone.foodcy.data.database.room.RoomUserFavorit
import com.capstone.foodcy.data.firebase.FirebaseUserAnswer
import com.capstone.foodcy.data.firebase.FirebaseUserCluster
import com.capstone.foodcy.data.firebase.FirebaseUserFavorit
import com.capstone.foodcy.databinding.FragmentSettingBinding
import com.capstone.foodcy.ui.setting.setreminder.SetReminderActivity
import com.capstone.foodcy.ui.setting.sync.SyncActivity
import com.google.firebase.database.*

class SettingFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentSettingBinding
    private lateinit var viewModel: SettingViewModel
    private lateinit var reff: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application))
            .get(SettingViewModel::class.java)

        binding.tvLinkLogout.setOnClickListener(this)
        binding.tvAlarm.setOnClickListener(this)
        binding.tvSync.setOnClickListener(this)
    }

    private fun logout() {
        deleteData()
        viewModel.setLogout(requireActivity())
    }

    private fun deleteData() {
        viewModel.getAllUserAnswer(activity!!.application).observe(viewLifecycleOwner, { ua ->
            if (ua.isNotEmpty()) {
                for (item in ua) {
                    viewModel.deleteUserAnswer(item, activity!!.application )
                }
                removeUserAnswerFromFirebase(ua)
            }
        })


        viewModel.getUserCluster(activity!!.application).observe(viewLifecycleOwner, { us ->
            if (us.isNotEmpty()) {
                for (item in us) {
                    viewModel.deleteUserCluster(item, activity!!.application)
                }
                removeUserClusterFromFirebase(us)
            }
        })

        viewModel.getUserFavorit(activity!!.application).observe(viewLifecycleOwner, { uf ->
            if (uf.isNotEmpty()) {
                for (item in uf) {
                    viewModel.deleteUserFavorit(item, activity!!.application)
                }
                removeUserFavoriteFromFirebase(uf)
            }
        })

    }

    private fun removeUserAnswerFromFirebase(list: List<RoomUserAnswer>) {
        Log.e("answer", list.toString())
        reff = FirebaseDatabase.getInstance().reference.child("user_answer")
        reff.orderByChild("uid").equalTo(viewModel.user?.uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        Log.e("exist", "ini eksis")
                        for (s in snapshot.children) {
                            s.ref.removeValue()
                        }
                        addUserAnswerToFirebase(list)
                    } else {
                        Log.e("gak exist", "ini gak eksis")
                        addUserAnswerToFirebase(list)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun removeUserClusterFromFirebase(list: List<RoomUserCluster>) {
        Log.e("cluster", list.toString())
        reff = FirebaseDatabase.getInstance().reference.child("user_cluster")
        reff.orderByChild("uid").equalTo(viewModel.user?.uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        Log.e("exist", "ini eksis")
                        for (s in snapshot.children) {
                            s.ref.removeValue()
                        }
                        addUserClusterToFirebase(list)
                    } else {
                        Log.e("gak exist", "ini gak eksis")
                        addUserClusterToFirebase(list)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "${error.message}", Toast.LENGTH_SHORT).show()
                }

            })
    }


    private fun removeUserFavoriteFromFirebase(list: List<RoomUserFavorit>) {
        Log.e("cluster", list.toString())
        reff = FirebaseDatabase.getInstance().reference.child("user_favorite")
        reff.orderByChild("uid").equalTo(viewModel.user?.uid.toString())
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        Log.e("exist", "ini eksis")
                        for (s in snapshot.children) {
                            s.ref.removeValue()
                        }
                        if (list.isNotEmpty()) {
                            addUserFavoritToFirebase(list)
                        }
                    } else {
                        if (list.isNotEmpty()) {
                            addUserFavoritToFirebase(list)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "${error.message}", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun addUserAnswerToFirebase(list: List<RoomUserAnswer>) {
        Log.e("answer", list.toString())
        for (l in list) {
            val firebaseUserAnswer = FirebaseUserAnswer(viewModel.user?.uid.toString(), l.idQuestion.toString().toInt(), l.score.toString(), l.answer.toString())
            reff = FirebaseDatabase.getInstance().reference.child("user_answer")
            reff.push().setValue(firebaseUserAnswer).addOnCompleteListener {
                if (!it.isSuccessful) {
                    Toast.makeText(context, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addUserClusterToFirebase(list: List<RoomUserCluster>) {
        Log.e("cluster", list.toString())
        reff = FirebaseDatabase.getInstance().reference.child("user_cluster")
        for (l in list) {
            val firebaseUserCluster = FirebaseUserCluster(
                viewModel.user?.uid.toString(),
                l.cluster.toString().toInt()
            )

            reff.push().setValue(firebaseUserCluster).addOnCompleteListener {
                if (!it.isSuccessful) {
                    Toast.makeText(context, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addUserFavoritToFirebase(list: List<RoomUserFavorit>) {
        Log.e("favorit", list.toString())
        reff = FirebaseDatabase.getInstance().reference.child("user_favorite")
        for (l in list) {
            val firebaseUserFavorite = FirebaseUserFavorit(
                viewModel.user?.uid.toString(),
                l.idFood
            )

            reff.push().setValue(firebaseUserFavorite).addOnCompleteListener {
                if (!it.isSuccessful) {
                    Toast.makeText(context, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun alarm(){
        val intent = Intent(activity, SetReminderActivity::class.java)
        startActivity(intent)
    }

    private fun sync() {
        val intent = Intent(context, SyncActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_link_logout -> {
                logout()
            }
            R.id.tv_alarm -> {
                alarm()
            }
            R.id.tv_sync -> {
                sync()
            }
        }
    }
}
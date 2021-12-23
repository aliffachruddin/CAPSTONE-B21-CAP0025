package com.capstone.foodcy.ui.profile

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.foodcy.R
import com.capstone.foodcy.data.entity.UserAnswerFb
import com.capstone.foodcy.data.entity.UserDetailEntity
import com.capstone.foodcy.databinding.FragmentProfileBinding
import com.capstone.foodcy.ui.quiz.QuizActivity
import com.capstone.foodcy.utils.`object`.Utils.hideSoftKeyBoard
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream


class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var imageUri : Uri
    private lateinit var ref: DatabaseReference
    private var user : FirebaseUser? = null
    private lateinit var listAnswer : MutableList<UserAnswerFb>
    private lateinit var viewModel: ProfileViewModel
    private lateinit var userDetail: UserDetailEntity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvMessageNull.visibility = View.GONE

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application))
            .get(ProfileViewModel::class.java)

        user = viewModel.user

        userDetail = viewModel.getUserDetail()
        showProfileDetail()

        binding.flAvatar.setOnClickListener(this)
        binding.btnQuiz.setOnClickListener(this)
        binding.btnUpdate.setOnClickListener(this)

        showUserAnswer()
    }

    private fun showProfileDetail() {
        if (userDetail.photoUrl != null) {
            Glide.with(this)
                .load(userDetail.photoUrl)
                .error(R.drawable.ic_broken_image)
                .into(binding.imgProfile)
        } else {
            Glide.with(this)
                .load(resources.getString(R.string.image_default))
                .error(R.drawable.ic_broken_image)
                .into(binding.imgProfile)
        }


        binding.edEmail.setText(userDetail.email)

        if (!userDetail.displayName.isNullOrEmpty()) {
            binding.edName.setText(userDetail.displayName)
        }

        if (!userDetail.phoneNumber.isNullOrEmpty()) {
            binding.edPhone.setText(userDetail.phoneNumber)
        }
    }

    /*
    private fun showQuizAnswer() {

        ref = FirebaseDatabase.getInstance().reference
        ref.child("userAnswer")
            .orderByChild("uid")
            .equalTo(user!!.uid)
            .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    listAnswer = mutableListOf()
                    listAnswer.clear()

                    for (s in snapshot.children) {
                        val answer = s.getValue(UserAnswerFb::class.java)
                        listAnswer.add(answer!!)
                    }

                    val listQuestions = viewModel.getAllQuestions()
                    val adapter = QuizUserAnswerAdapter(listAnswer, listQuestions)

                    with(binding) {
                            rvUserAnswer.layoutManager = LinearLayoutManager(context)
                            rvUserAnswer.adapter = adapter
                            rvUserAnswer.setHasFixedSize(true)
                            btnQuiz.text = context?.getString(R.string.retake_the_quiz)
                        }
                } else {
                    binding.tvMessageNull.visibility = View.VISIBLE
                }
                showLoading(false)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Error Read Data", "loadPost:onCancelled, ${error.toException()}")
            }

        })
    }

     */

    private fun showUserAnswer() {
        viewModel.getAllUserAnswer(activity!!.application).observe(viewLifecycleOwner, { ua ->
            if (ua.isNotEmpty()) {
                showLoading(false)
                binding.tvMessageNull.visibility = View.GONE
                val listQuestions = viewModel.getAllQuestions()
                val adapter = QuizUserAnswerAdapter(ua, listQuestions)

                with(binding) {
                    rvUserAnswer.layoutManager = LinearLayoutManager(context)
                    rvUserAnswer.adapter = adapter
                    rvUserAnswer.setHasFixedSize(true)
                    btnQuiz.text = context?.getString(R.string.retake_the_quiz)
                }
            } else {
                showLoading(false)
                binding.tvMessageNull.visibility = View.VISIBLE
            }
        })

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_avatar -> {
                onClickCamera()
            }

            R.id.btn_quiz -> {
                val intent = Intent(activity, QuizActivity::class.java)
                startActivity(intent)
            }

            R.id.btn_update -> {
                if (context != null && view != null) {
                    hideSoftKeyBoard(context!!, view!!)
                }

                saveChanges()

                binding.btnUpdate.text = resources.getString(R.string.edit)

            }
        }
    }

    private fun onClickCamera() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Change Profile Image")
        builder.setMessage("Click upload to change with a new picture")
        builder.setPositiveButton("Upload") { dialog, _ ->
            intentCamera()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun intentCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            activity?.packageManager?.let {
                intent.resolveActivity(it).also {
                    startActivityForResult(intent, REQUEST_CAMERA)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            val imgBitmap = data?.extras?.get("data") as Bitmap
            uploadImage(imgBitmap)
        }
    }

    private fun uploadImage(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref = FirebaseStorage.getInstance().reference.child("img/${userDetail.uid}")

        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image = baos.toByteArray()

        ref.putBytes(image)
            .addOnCompleteListener { upload ->
                if (upload.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener { uri ->
                        uri.result?.let {
                            imageUri = it
                            binding.imgProfile.setImageBitmap(imgBitmap)
                            binding.btnUpdate.text = resources.getString(R.string.save_changes)
                        }
                    }
                }
            }
    }

    private fun saveChanges() {
        val image = when {
            ::imageUri.isInitialized -> imageUri
            userDetail.photoUrl == null -> Uri.parse(resources.getString(R.string.image_default))
            else -> userDetail.photoUrl
        }

        val name = binding.edName.text.toString().trim()

        if (name.isEmpty()){
            binding.edName.error = "You must fill the display name"
            binding.edName.requestFocus()
            return
        }

        UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .setPhotoUri(image)
            .build().also { profile ->
                user?.updateProfile(profile)?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(activity, resources.getString(R.string.profile_updated), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    private fun showLoading(isVisible : Boolean) {
        if (isVisible) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val REQUEST_CAMERA = 100
    }

}
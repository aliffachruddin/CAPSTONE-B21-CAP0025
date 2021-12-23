package com.capstone.foodcy.ui.quiz

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.capstone.foodcy.R
import com.capstone.foodcy.data.database.room.RoomUserCluster
import com.capstone.foodcy.databinding.ActivityResultBinding
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.lang.Exception

import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var tflite : Interpreter
    private lateinit var viewModel: ResultViewModel
    private val delay: Int = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ResultViewModel::class.java)

        val list: ArrayList<String> = intent.getStringArrayListExtra("EXTRA_SCORES")!!

        try {
            tflite = Interpreter(loadModelFile())
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        val prediction = doInference(list)
        for (pr in prediction) {
            val max = pr.maxOrNull()
            for ((cluster,p) in pr.withIndex()){
                if (p == max) {
                    //removeClusterFromDatabase(cluster)
                    viewModel.getUserCluster(application).observe(this, { us ->
                        if (us.isEmpty()) {
                            addUserClusterToRoom(cluster)
                        } else {
                            updateUserClusterToRoom(cluster)
                        }
                    })

                    Log.e("max cluster", cluster.toString())
                }
                Log.e("pr", p.toString())
            }
        }
    }

    @Throws(IOException::class)
    private fun loadModelFile(): MappedByteBuffer {
        val fileDescriptor = this.assets.openFd("food.tflite")
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel: FileChannel = inputStream.getChannel()
        val startOffset = fileDescriptor.startOffset
        val declareLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declareLength)
    }

    private fun doInference(inputList: List<String>): Array<FloatArray> {
        val inputVal = FloatArray(inputList.size)

        for ((i, input) in inputList.withIndex()) {
            inputVal[i] = input.toFloat()
        }

        for (input in inputVal){
            Log.e("input", input.toString())
        }

        val output = Array(1) {
            FloatArray(
                20
            )
        }

        tflite.run(inputVal, output)
        return output
    }

    private fun addUserClusterToRoom(cluster: Int) {
        val user = viewModel.user?.uid
        val item = RoomUserCluster(user.toString(),cluster.toString())
        viewModel.insert(item, application)

        calculating()
    }

    private fun updateUserClusterToRoom(cluster: Int) {
        val user = viewModel.user?.uid.toString()
        val userCluster = RoomUserCluster(user, cluster.toString())
        viewModel.update(userCluster, application)

        calculating()
    }

    private fun calculating() {
        binding.tvCalculating.text = getString(R.string.calculating)

        Handler(mainLooper).postDelayed({
            binding.progressBar.visibility = View.GONE
            finish()
        }, delay.toLong())

    }


    /*
    private fun removeClusterFromDatabase(cluster : Int) {

        val dbRef = FirebaseDatabase.getInstance().reference
        dbRef.child("userCluster")
            .orderByChild("uid")
            .equalTo(viewModel.user?.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (s in dataSnapshot.children) {
                        s.ref.removeValue()
                        addClusterToDatabase(cluster)
                    }
                } else {
                    addClusterToDatabase(cluster)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("Remove Answer: ", "onCancelled", databaseError.toException())
            }
        })
    }

    private fun addClusterToDatabase(cluster : Int) {
        val ref = FirebaseDatabase.getInstance().getReference("userCluster")
        val idUserCluster = ref.push().key.toString()
        val userCluster = UserCluster(viewModel.user?.uid.toString(), cluster)
        ref.child(idUserCluster).setValue(userCluster).addOnCompleteListener {
            if (!it.isSuccessful) {
                Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
            } else {
                val fragment = RecommendationFragment()
                loadRecommendation(fragment)
            }
        }
    }

     */
}
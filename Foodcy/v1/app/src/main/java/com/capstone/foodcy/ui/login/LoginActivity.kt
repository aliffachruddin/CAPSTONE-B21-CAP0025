package com.capstone.foodcy.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.capstone.foodcy.R
import com.capstone.foodcy.data.entity.UserEntity
import com.capstone.foodcy.databinding.ActivityLoginBinding
import com.capstone.foodcy.ui.register.RegisterActivity
import com.capstone.foodcy.utils.`object`.Utils
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(LoginViewModel::class.java)

        binding.tvLinkRegister.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_link_register -> {
                intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_login -> {
                Utils.hideSoftKeyBoard(applicationContext, v)
                val email = binding.edEmail.text.toString().trim()
                val password = binding.edPassword.text.toString()

                val isValidate = validate(email, password)

                if (!isValidate) {
                    return
                }

                login(email, password)
            }
        }
    }

    private fun validate(email: String, password: String) : Boolean {
        var isValidate = true
        if (email.isEmpty()) {
            binding.edEmail.error = "Email must be filled"
            binding.edEmail.requestFocus()
            isValidate = false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edEmail.error = "Email not valid"
            binding.edEmail.requestFocus()
            isValidate = false
        }

        if (password.isEmpty() || password.length < 6) {
            binding.edPassword.error = "Password must be filled at least 6 chars"
            binding.edPassword.requestFocus()
            isValidate = false
        }

        return isValidate
    }

    private fun login(email: String, password: String) {
        val user = UserEntity(email, password)
        viewModel.setLogin(user, this)
    }
}
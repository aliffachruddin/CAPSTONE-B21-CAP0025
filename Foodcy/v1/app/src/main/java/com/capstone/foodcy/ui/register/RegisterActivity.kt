package com.capstone.foodcy.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.capstone.foodcy.R
import com.capstone.foodcy.data.entity.UserEntity
import com.capstone.foodcy.databinding.ActivityRegisterBinding
import com.capstone.foodcy.ui.login.LoginActivity
import com.capstone.foodcy.utils.`object`.Utils

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var activityRegisterBinding : ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(RegisterViewModel::class.java)

        activityRegisterBinding.tvLinkLogin.setOnClickListener(this)
        activityRegisterBinding.btnRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_link_login -> {
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_register -> {
                Utils.hideSoftKeyBoard(applicationContext, v)
                val email = activityRegisterBinding.edEmail.text.toString().trim()
                val password = activityRegisterBinding.edPassword.text.toString()
                val isValidate = validate(email, password)
                if (!isValidate) {
                    return
                }

                register(email, password)
            }
        }
    }

    private fun validate(email: String, password: String) : Boolean {
        var isValidate = true

        if (email.isEmpty()) {
            activityRegisterBinding.edEmail.error = "Email must be filled"
            activityRegisterBinding.edEmail.requestFocus()
            isValidate = false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            activityRegisterBinding.edEmail.error = "Email not valid"
            activityRegisterBinding.edEmail.requestFocus()
            isValidate = false
        }

        if (password.isEmpty() || password.length < 6) {
            activityRegisterBinding.edPassword.error = "Password must be filled at least 6 chars"
            activityRegisterBinding.edPassword.requestFocus()
            isValidate = false
        }

        return isValidate
    }

    private fun register(email: String, password: String) {
        val user = UserEntity(email, password)
        viewModel.setRegister(user, this)
    }
}
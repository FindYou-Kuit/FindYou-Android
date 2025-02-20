package com.example.findu.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.findu.R
import com.example.findu.databinding.ActivityLoginBinding
import com.example.findu.presentation.ui.login.viewmodel.LoginViewModel
import com.example.findu.presentation.ui.main.MainActivity
import com.example.findu.presentation.ui.signup.SignupActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var isPasswordVisible = false
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        observeLoginResult()
    }

    private fun initListener() {
        binding.ivLoginPasswordVisible.setOnClickListener {
            togglePasswordVisibility()
        }

        binding.tvLoginSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.clLoginButton.setOnClickListener {
            validateLogin()
        }
    }

    private fun togglePasswordVisibility() {
        isPasswordVisible = !isPasswordVisible
        if (isPasswordVisible) {
            binding.etLoginPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.ivLoginPasswordVisible.setImageResource(R.drawable.ic_password_on)
        } else {
            binding.etLoginPassword.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.ivLoginPasswordVisible.setImageResource(R.drawable.ic_password_off)
        }

        binding.etLoginPassword.setSelection(binding.etLoginPassword.text.length)
    }

    private fun validateLogin() {
        val email = binding.etLoginEmail.text.toString().trim()
        val password = binding.etLoginPassword.text.toString().trim()

        when {
            email.isEmpty() -> showToast("이메일을 입력해주세요")
            password.isEmpty() -> showToast("비밀번호를 입력해주세요")
            else -> loginViewModel.postLogin(email, password)
        }
    }

    private fun observeLoginResult() {
        lifecycleScope.launch {
            loginViewModel.loginResult.collect { result ->
                result?.let {
                    if (it) {
                        showToast("로그인 성공!")
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        showToast("로그인 실패! 계정을 확인해주세요.")
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
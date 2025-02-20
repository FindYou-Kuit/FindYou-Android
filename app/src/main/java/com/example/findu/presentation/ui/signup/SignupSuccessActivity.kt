package com.example.findu.presentation.ui.signup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.findu.R
import com.example.findu.databinding.ActivitySignupSuccessBinding
import com.example.findu.presentation.ui.main.MainActivity

class SignupSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNickname()
        initListener()
    }

    private fun initListener() {
        binding.clSignupSuccessButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setNickname() {
        val nickname = intent.getStringExtra("nickname") ?: "회원"
        binding.tvSignupSuccessTitle.text = getString(R.string.signup_success_title, nickname)
    }
}
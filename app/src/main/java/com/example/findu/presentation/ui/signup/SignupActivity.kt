package com.example.findu.presentation.ui.signup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.findu.R
import com.example.findu.databinding.ActivitySignupBinding
import com.example.findu.presentation.ui.signup.viewmodel.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private var isPasswordVisible = false
    private var isPasswordCheckVisible = false
    private var isEmailValid = false
    private val signupViewModel: SignupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListener()
        observeViewModel()
    }

    private fun initListener() {
        binding.ivSignupPasswordVisible.setOnClickListener {
            togglePasswordVisibility(0)
        }

        binding.ivSignupPasswordCheckVisible.setOnClickListener {
            togglePasswordVisibility(1)
        }

        binding.clSignupEmailButton.setOnClickListener {
            checkEmailAvailability()
        }

        binding.etSignupEmail.addTextChangedListener(inputWatcher)
        binding.etSignupPassword.addTextChangedListener(inputWatcher)
        binding.etSignupPasswordCheck.addTextChangedListener(inputWatcher)
        binding.etSignupNickname.addTextChangedListener(inputWatcher)

        binding.clSignupButton.setOnClickListener {
            attemptSignup()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            signupViewModel.emailCheckResult.collect { result ->
                result?.let {
                    if (it) {
                        isEmailValid = true
                        updateTextAlert(
                            binding.tvSignupEmailAlert,
                            getString(R.string.signup_email_button_right),
                            R.color.green1
                        )
                    } else {
                        isEmailValid = false
                        updateTextAlert(
                            binding.tvSignupEmailAlert,
                            getString(R.string.signup_email_button_wrong),
                            R.color.red1
                        )
                    }
                    updateSignupButtonState()
                }
            }
        }

        lifecycleScope.launch {
            signupViewModel.signupResult.collect { result ->
                result?.let {
                    if (it) {
                        navigateToSignupSuccess()
                    } else {
                        showToast(getString(R.string.signup_failed))
                    }
                }
            }
        }
    }

    private fun navigateToSignupSuccess() {
        val nickname = binding.etSignupNickname.text.toString()
        val intent = Intent(this, SignupSuccessActivity::class.java)
        intent.putExtra("nickname", nickname)
        startActivity(intent)
        finish()
    }

    private fun checkEmailAvailability() {
        val email = binding.etSignupEmail.text.toString()

        if (email.isNotEmpty() && email.contains("@")) {
            signupViewModel.checkEmail(email)
        } else {
            isEmailValid = false
            updateTextAlert(
                binding.tvSignupEmailAlert,
                getString(R.string.signup_email_invalid),
                R.color.red1
            )
            updateSignupButtonState()
        }
    }

    private fun togglePasswordVisibility(type: Int) {
        if (type == 0) {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                binding.etSignupPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.ivSignupPasswordVisible.setImageResource(R.drawable.ic_password_on)
            } else {
                binding.etSignupPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.ivSignupPasswordVisible.setImageResource(R.drawable.ic_password_off)
            }

            binding.etSignupPassword.setSelection(binding.etSignupPassword.text.length)
        } else {
            isPasswordCheckVisible = !isPasswordCheckVisible
            if (isPasswordCheckVisible) {
                binding.etSignupPasswordCheck.inputType =
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.ivSignupPasswordCheckVisible.setImageResource(R.drawable.ic_password_on)
            } else {
                binding.etSignupPasswordCheck.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.ivSignupPasswordCheckVisible.setImageResource(R.drawable.ic_password_off)
            }

            binding.etSignupPasswordCheck.setSelection(binding.etSignupPasswordCheck.text.length)
        }
    }

    private fun validatePassword(password: String) {
        val passwordAlert = binding.tvSignupPasswordAlert

        if (binding.etSignupPassword.text.toString().isNotEmpty()) {
            if (isValidPassword(password)) {
                updateTextAlert(
                    passwordAlert,
                    getString(R.string.signup_password_right),
                    R.color.green1
                )
            } else {
                updateTextAlert(
                    passwordAlert,
                    getString(R.string.signup_password_wrong),
                    R.color.red1
                )
            }
        } else {
            passwordAlert.text = ""
        }
    }

    private fun validatePasswordMatch() {
        val password = binding.etSignupPassword.text.toString()
        val confirmPassword = binding.etSignupPasswordCheck.text.toString()
        val passwordCheckAlert = binding.tvSignupPasswordCheckAlert

        if (password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            if (password == confirmPassword) {
                updateTextAlert(
                    passwordCheckAlert,
                    getString(R.string.signup_password_check_right),
                    R.color.green1
                )
            } else {
                updateTextAlert(
                    passwordCheckAlert,
                    getString(R.string.signup_password_check_wrong),
                    R.color.red1
                )
            }
        } else {
            passwordCheckAlert.text = ""
        }
    }

    private fun isValidNickname(nickname: String): Boolean = nickname.length in 1..8

    private fun isSignupValid(): Boolean {
        val password = binding.etSignupPassword.text.toString()
        val confirmPassword = binding.etSignupPasswordCheck.text.toString()
        val nickname = binding.etSignupNickname.text.toString()

        return isEmailValid && isValidPassword(password) && password == confirmPassword && isValidNickname(nickname)
    }

    private fun updateSignupButtonState() {
        binding.clSignupButton.isEnabled = isSignupValid()
    }

    private fun attemptSignup() {
        if (isSignupValid()) {
            val email = binding.etSignupEmail.text.toString()
            val password = binding.etSignupPassword.text.toString()
            val nickname = binding.etSignupNickname.text.toString()

            signupViewModel.postSignup(email, password, nickname)
        }
    }

    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{8,20}$"
        return password.matches(passwordPattern.toRegex())
    }

    private fun updateTextAlert(textView: TextView, message: String, colorResId: Int) {
        textView.text = message
        textView.setTextColor(ContextCompat.getColor(this, colorResId))
    }

    private val inputWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            validatePassword(binding.etSignupPassword.text.toString())
            validatePasswordMatch()
            updateSignupButtonState()
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

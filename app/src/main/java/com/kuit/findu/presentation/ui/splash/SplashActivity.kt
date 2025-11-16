package com.kuit.findu.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kuit.findu.R
import com.kuit.findu.databinding.ActivitySplashBinding
import com.kuit.findu.domain.usecase.SetDeviceIdUseCase
import com.kuit.findu.domain.usecase.token.GetAccessTokenUseCase
import com.kuit.findu.presentation.ui.login.LoginActivity
import com.kuit.findu.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var getAccessTokenUseCase: GetAccessTokenUseCase

    @Inject
    lateinit var setDeviceIdUseCase: SetDeviceIdUseCase

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val deviceId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
            ?: "unknown_device_id"

        setDeviceIdUseCase(deviceId=deviceId)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            val accessToken = getAccessTokenUseCase()
            delay(1000)
            setGif()
            delay(2000)
            if (accessToken.isEmpty()) {
                navigateToLogin()
            } else {
                navigateToMain()
            }
        }


    }
    private fun setGif(){
        Glide.with(this)
            .asGif()
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: GifDrawable,
                    model: Any,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    resource.setLoopCount(1)
                    resource.stop()
                    return false
                }

            })
            .load(R.raw.splash)
            .into(binding.ivSplashGif)

    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
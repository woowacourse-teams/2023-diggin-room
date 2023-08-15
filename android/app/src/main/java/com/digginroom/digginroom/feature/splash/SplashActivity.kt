package com.digginroom.digginroom.feature.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.data.di.ViewModelFactory
import com.digginroom.digginroom.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory.getInstance(applicationContext).splashViewModelFactory
        )[SplashViewModel::class.java]
    }
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSplashBinding()
        validateToken()
    }

    private fun initSplashBinding() {
        binding =
            DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)
                .also {
                    it.lifecycleOwner = this
                    it.viewModel = splashViewModel
                    it.resultListener = SplashResultListener(this)
                }
    }

    private fun validateToken() {
        Handler(mainLooper).postDelayed(
            { splashViewModel.validateToken() },
            SHOWING_TIME
        )
    }

    companion object {

        private const val SHOWING_TIME: Long = 1000
    }
}

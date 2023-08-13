package com.digginroom.digginroom.feature.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.data.di.ViewModelFactory
import com.digginroom.digginroom.databinding.ActivitySplashBinding
import com.digginroom.digginroom.feature.ResultListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory.getInstance(applicationContext).splashViewModelFactory
        )[SplashViewModel::class.java]
    }
    private val splashResultListener: ResultListener by lazy {
        SplashResultListener(this)
    }
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSplashBinding()
        initSplashObserver()
        validateToken()
    }

    private fun initSplashBinding() {
        binding =
            DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)
                .also {
                    it.viewModel = splashViewModel
                    it.resultListener = splashResultListener
                }
    }

    private fun initSplashObserver() {
        splashViewModel.tokenState.observe(this) { state ->
            when (state) {
                TokenState.VALID -> splashResultListener.onSucceed()
                TokenState.INVALID -> splashResultListener.onFailed()
                else -> {}
            }
        }
    }

    private fun validateToken() {
        runBlocking {
            delay(SHOWING_TIME)
            splashViewModel.validateToken()
        }
        Log.d("woogi", "changed: ${splashViewModel.tokenState.value}")
    }

    companion object {

        private const val SHOWING_TIME: Long = 3500
    }
}

package com.digginroom.digginroom.feature.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.ActivitySplashBinding
import com.digginroom.digginroom.feature.genretaste.GenreTasteActivity
import com.digginroom.digginroom.feature.login.LoginActivity
import com.digginroom.digginroom.feature.login.LoginUiState
import com.digginroom.digginroom.feature.room.RoomActivity
import com.dygames.androiddi.ViewModelDependencyInjector.injectViewModel

class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by lazy {
        ViewModelProvider(
            this,
            injectViewModel<SplashViewModel>()
        )[SplashViewModel::class.java]
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
                    it.lifecycleOwner = this
                    it.viewModel = splashViewModel
                }
    }

    private fun initSplashObserver() {
        splashViewModel.loginUiState.observe(this) {
            finish()
            when (it) {
                is LoginUiState.Succeed.NotSurveyed -> GenreTasteActivity.start(this)
                is LoginUiState.Succeed.Surveyed -> RoomActivity.start(this)
                else -> LoginActivity.start(this)
            }
        }
    }

    private fun validateToken() {
        splashViewModel.validateToken()
    }
}

package com.digginroom.digginroom.views.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.data.di.ViewModelFactory
import com.digginroom.digginroom.databinding.ActivityLoginBinding
import com.digginroom.digginroom.viewmodels.LoginViewModel

class LoginActivity : AppCompatActivity(), ResultListener {

    private lateinit var splashScreen: SplashScreen
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory.getInstance(applicationContext).loginViewModelFactory
        )[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSplashScreen()
        initLoginBinding()
    }

    private fun initSplashScreen() {
        splashScreen = installSplashScreen()
    }

    private fun initLoginBinding() {
        binding =
            DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
                .also {
                    it.lifecycleOwner = this
                    it.resultListener = this
                    it.navigateToJoinView = ::onStartJoin
                    it.viewModel = loginViewModel
                }
    }

    private fun onStartJoin() {
        JoinActivity.start(this)
    }

    override fun onSucceed() {
        RoomActivity.start(this)
    }

    override fun onFailed() {
        with(binding) {
            loginEtInputId.text.clear()
            loginEtInputPassword.text.clear()
        }
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)

            context.startActivity(intent)
        }
    }
}

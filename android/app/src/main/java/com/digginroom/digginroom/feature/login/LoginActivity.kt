package com.digginroom.digginroom.feature.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.data.di.ViewModelFactory
import com.digginroom.digginroom.databinding.ActivityLoginBinding
import com.digginroom.digginroom.feature.login.navigator.LoginNavigator

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory.getInstance(applicationContext).loginViewModelFactory,
        )[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLoginBinding()
    }

    private fun initLoginBinding() {
        binding =
            DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
                .also {
                    it.lifecycleOwner = this
                    it.resultListener = LoginResultListener(
                        context = this,
                        inputTexts = listOf(
                            it.loginEtInputPassword,
                            it.loginEtInputPassword,
                        ),
                    )
                    it.navigator = LoginNavigator(this)
                    it.viewModel = loginViewModel
                }
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)

            context.startActivity(intent)
        }
    }
}

package com.digginroom.digginroom.views.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.ActivityLoginBinding
import com.digginroom.digginroom.viewmodels.LoginViewModel

class LoginActivity : AppCompatActivity(), ResultListener {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(
            this,
            LoginViewModel.getLoginViewModelFactory()
        )[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLoginBinding()
    }

    private fun initLoginBinding() {
        binding = DataBindingUtil
            .setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
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
}

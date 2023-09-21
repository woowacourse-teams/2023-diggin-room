package com.digginroom.digginroom.feature.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.ActivityLoginBinding
import com.digginroom.digginroom.feature.genretaste.GenreTasteActivity
import com.digginroom.digginroom.feature.join.JoinActivity
import com.digginroom.digginroom.feature.room.RoomActivity
import com.digginroom.digginroom.model.AccountModel
import com.dygames.androiddi.ViewModelDependencyInjector.injectViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(
            this,
            injectViewModel<LoginViewModel>()
        )[LoginViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLoginBinding()
        initLoginStateObserver()
        initGoogleLoginObserver()
        initJoinClickListener()
    }

    private fun initLoginBinding() {
        binding =
            DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
                .also {
                    it.lifecycleOwner = this
                    it.viewModel = loginViewModel
                    it.account = AccountModel()
                }
    }

    private fun initLoginStateObserver() {
        loginViewModel.uiState.observe(this) {
            when (it) {
                is LoginUiState.Succeed.NotSurveyed -> {
                    finish()
                    GenreTasteActivity.start(this)
                }

                is LoginUiState.Succeed.Surveyed -> {
                    finish()
                    RoomActivity.start(this)
                }

                is LoginUiState.Failed -> {
                    binding.account = it.account
                }

                else -> {}
            }
        }
    }

    private fun initGoogleLoginObserver() {
        val googleLoginResultLauncher = getSocialLoginResultLauncher(SocialLogin.Google)

        loginViewModel.googleLoginEvent.observe(this) {
            googleLoginResultLauncher.launch(SocialLogin.Google.getIntent(this))
        }
    }

    private fun getSocialLoginResultLauncher(socialLogin: SocialLogin): ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                socialLogin
                    .getIdToken(result)
                    ?.let { idToken ->
                        loginViewModel.login(idToken)
                    }
            }
        }

    private fun initJoinClickListener() {
        binding.loginTvJoin.setOnClickListener {
            JoinActivity.start(this)
        }
    }

    companion object {

        private const val RESULT_OK = -1

        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }

            context.startActivity(intent)
        }
    }
}

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
import com.digginroom.digginroom.data.di.ViewModelFactory
import com.digginroom.digginroom.databinding.ActivityLoginBinding
import com.digginroom.digginroom.feature.genretaste.GenreTasteActivity
import com.digginroom.digginroom.feature.join.JoinActivity
import com.digginroom.digginroom.feature.room.RoomActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory.getInstance(applicationContext).loginViewModelFactory
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
                }
    }

    private fun initLoginStateObserver() {
        loginViewModel.state.observe(this) {
            when (it) {
                is LoginState.Succeed.NotSurveyed -> {
                    finish()
                    GenreTasteActivity.start(this)
                }
                is LoginState.Succeed.Surveyed -> {
                    finish()
                    RoomActivity.start(this)
                }
                is LoginState.Failed -> {
                    binding.loginEtInputId.text.clear()
                    binding.loginEtInputPassword.text.clear()
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
            val intent = Intent(context, LoginActivity::class.java)

            context.startActivity(intent)
        }
    }
}

package com.digginroom.digginroom.feature.join

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.ActivityJoinBinding
import com.digginroom.digginroom.feature.login.LoginActivity
import com.dygames.androiddi.ViewModelDependencyInjector.injectViewModel

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding
    private val joinViewModel: JoinViewModel by lazy {
        ViewModelProvider(
            this,
            injectViewModel<JoinViewModel>()
        )[JoinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initJoinBinding()
        initJoinStateObserver()
    }

    private fun initJoinBinding() {
        binding =
            DataBindingUtil.setContentView<ActivityJoinBinding>(this, R.layout.activity_join).also {
                it.lifecycleOwner = this
                it.account = joinViewModel.accountModel
                it.viewModel = joinViewModel
            }
    }

    private fun initJoinStateObserver() {
        joinViewModel.uiState.observe(this) {
            when (it) {
                is JoinUiState.InProgress -> binding.joinVerification = it.joinVerification

                is JoinUiState.Succeed -> {
                    Toast.makeText(this, R.string.join_succeed_message, Toast.LENGTH_SHORT).show()
                    LoginActivity.start(this)
                }

                is JoinUiState.Failed -> {
                    binding.account = it.account
                    Toast.makeText(this, R.string.join_failed_message, Toast.LENGTH_SHORT).show()
                }

                is JoinUiState.Cancel -> finish()

                else -> {}
            }
        }
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, JoinActivity::class.java)

            context.startActivity(intent)
        }
    }
}

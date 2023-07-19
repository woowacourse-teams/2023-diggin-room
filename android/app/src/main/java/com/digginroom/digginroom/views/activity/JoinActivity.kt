package com.digginroom.digginroom.views.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.ActivityJoinBinding
import com.digginroom.digginroom.viewmodels.JoinViewModel

class JoinActivity : AppCompatActivity(), ResultListener {

    private lateinit var binding: ActivityJoinBinding
    private val joinViewModel: JoinViewModel by lazy {
        ViewModelProvider(
            this,
            JoinViewModel.getJoinViewModelFactory()
        )[JoinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initJoinBinding()
    }

    private fun initJoinBinding() {
        binding = DataBindingUtil
            .setContentView<ActivityJoinBinding>(this, R.layout.activity_join)
            .also {
                it.lifecycleOwner = this
                it.resultListener = this
                it.viewModel = joinViewModel
            }
    }

    override fun onSucceed() {
        RoomActivity.start(this)
        finish()
    }

    override fun onFailed() {
        with(binding) {
            joinEtInputId.text.clear()
            joinEtInputPassword.text.clear()
        }
    }
}

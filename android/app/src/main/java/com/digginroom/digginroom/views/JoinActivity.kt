package com.digginroom.digginroom.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.ActivityJoinBinding
import com.digginroom.digginroom.viewmodels.JoinViewModel

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding
    private val joinViewModel: JoinViewModel by lazy {
        ViewModelProvider(
            this,
            JoinViewModel.getJoinViewModelFactory(
                joinSucceed = ::navigateToRoomView,
                joinFailed = ::clearInputIdAndPassword
            )
        )[JoinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initJoinBinding()
        initJoinButtonListener()
        initObservers()
        initIdTextChangedListener()
        initPasswordTextChangedListener()
        initReInputPasswordTextChangedListener()
    }

    private fun initJoinBinding() {
        binding = DataBindingUtil
            .setContentView<ActivityJoinBinding>(this, R.layout.activity_join)
            .also {
                it.lifecycleOwner = this
            }
    }

    // todo: 바인딩 어댑터로 빠질 예정
    private fun initObservers() {
        joinViewModel.isValidId.observe(this) {
            binding.joinTvIdFormError.isVisible = !it
        }
        joinViewModel.isValidPassword.observe(this) {
            binding.joinTvPasswordFormError.isVisible = !it
        }
        joinViewModel.isEqualPassword.observe(this) {
            binding.joinTvPasswordNotEqual.isVisible = !it
        }
        joinViewModel.isJoinAble.observe(this) {
            binding.joinButtonJoin.isEnabled = it
        }
    }

    private fun initJoinButtonListener() {
        with(binding) {
            joinButtonJoin.setOnClickListener {
                joinViewModel.join(
                    id = joinTvId.text.toString(),
                    password = joinTvPassword.text.toString()
                )
            }
        }
    }

    private fun initIdTextChangedListener() {
        binding.joinEtInputId.doAfterTextChanged { id ->
            joinViewModel.validateId(id.toString())
            joinViewModel.validateJoinAble()
        }
    }

    private fun initPasswordTextChangedListener() {
        binding.joinEtInputPassword.doAfterTextChanged { id ->
            joinViewModel.validatePassword(id.toString())
            joinViewModel.validateJoinAble()
        }
    }

    private fun initReInputPasswordTextChangedListener() {
        with(binding) {
            joinEtReInputPassword.doAfterTextChanged { id ->
                joinViewModel.validatePasswordEquality(
                    password = joinEtInputPassword.text.toString(),
                    reInputPassword = joinEtReInputPassword.text.toString()
                )
                joinViewModel.validateJoinAble()
            }
        }
    }

    private fun navigateToRoomView() {
        // todo: RoomView가 구성되어 있지 않은 상황에서 임시로 구현
        val intent = MainActivity.getIntent(this)

        startActivity(intent)
    }

    private fun clearInputIdAndPassword() {
        with(binding) {
            joinEtInputId.text.clear()
            joinEtInputPassword.text.clear()
        }
    }
}

package com.digginroom.digginroom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.databinding.ActivityJoinBinding
import com.digginroom.model.join.JoinState

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding
    private val joinViewModel: JoinViewModel by lazy {
        ViewModelProvider(
            this,
            JoinViewModel.getJoinViewModelFactory()
        )[JoinViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        initJoinObserver()
        initJoinButtonListener()
    }

    private fun initJoinObserver() {
        joinViewModel.state.observe(this) { joinState ->
            when (joinState) {
                is JoinState.Success -> navigateToRoomView()
                is JoinState.Retry -> clearInputIdAndPassword()
            }
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

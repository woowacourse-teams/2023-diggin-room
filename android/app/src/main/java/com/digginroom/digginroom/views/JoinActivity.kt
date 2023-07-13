package com.digginroom.digginroom.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        initJoinButtonListener()
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

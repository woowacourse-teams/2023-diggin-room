package com.digginroom.digginroom.views.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.digginroom.digginroom.R
import com.digginroom.digginroom.data.datasource.remote.RoomRemoteDataSource
import com.digginroom.digginroom.data.repository.DefaultRoomRepository
import com.digginroom.digginroom.databinding.ActivityRoomBinding
import com.digginroom.digginroom.viewmodels.RoomViewModel

class RoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room)
        binding.lifecycleOwner = this
        binding.roomViewModel = RoomViewModel(
            mutableListOf(),
            DefaultRoomRepository(
                RoomRemoteDataSource(),
            ),
        ).also {
            it.findNextRoom()
            it.findNextRoom()
            it.findNextRoom()
            it.findNextRoom()
            it.findNextRoom()
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RoomActivity::class.java)
            context.startActivity(intent)
        }
    }
}

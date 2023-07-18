package com.digginroom.digginroom.views.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.ActivityRoomPagerBinding
import com.digginroom.digginroom.viewmodels.RoomPagerViewModel
import com.digginroom.digginroom.views.model.RoomModel

class RoomPagerActivity : AppCompatActivity() {
    lateinit var binding: ActivityRoomPagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_pager)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_room_pager)
        val viewModel = RoomPagerViewModel()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.data.observe(this, {
            binding.roomPager.data = it
            binding.roomPager.updateData()
        })

        viewModel.addData(RoomModel(R.drawable.kitty))
    }
}

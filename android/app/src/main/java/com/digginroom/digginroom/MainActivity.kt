package com.digginroom.digginroom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.digginroom.digginroom.data.datasource.RoomRemoteDataSource
import com.digginroom.digginroom.data.repository.DefaultRoomRepository
import com.digginroom.digginroom.databinding.ActivityMainBinding
import com.digginroom.digginroom.viewmodels.RoomViewModel
import com.digginroom.digginroom.viewmodels.YoutubeRoomCacheStrategy
import com.digginroom.model.room.Room
import com.digginroom.model.room.Song

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.roomViewModel = RoomViewModel(
            mutableListOf(
                Room(
                    "ucZl6vQ_8Uo",
                    Song("", "", "", emptyList(), emptyList()),
                    false
                ),
                Room(
                    "ucZl6vQ_8Uo",
                    Song("", "", "", emptyList(), emptyList()),
                    false
                ),
                Room(
                    "ucZl6vQ_8Uo",
                    Song("", "", "", emptyList(), emptyList()),
                    false
                ),
                Room(
                    "ucZl6vQ_8Uo",
                    Song("", "", "", emptyList(), emptyList()),
                    false
                ),
                Room(
                    "ucZl6vQ_8Uo",
                    Song("", "", "", emptyList(), emptyList()),
                    false
                )
            ),
            YoutubeRoomCacheStrategy(),
            DefaultRoomRepository(
                RoomRemoteDataSource()
            )
        )
    }
}

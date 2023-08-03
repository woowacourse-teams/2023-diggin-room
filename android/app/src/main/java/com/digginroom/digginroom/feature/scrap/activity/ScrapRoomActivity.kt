package com.digginroom.digginroom.feature.scrap.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.data.di.ViewModelFactory
import com.digginroom.digginroom.databinding.ActivityScrapRoomBinding
import com.digginroom.digginroom.feature.getSerializableCompat
import com.digginroom.digginroom.feature.scrap.ScrapRoomViewModel
import com.digginroom.digginroom.model.RoomModels

class ScrapRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrapRoomBinding
    private lateinit var scrapRoomViewModel: ScrapRoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initScrapRoomViewModel()
        initScrapRoomBinding()
    }

    private fun initScrapRoomBinding() {
        binding = DataBindingUtil.setContentView<ActivityScrapRoomBinding>(
            this,
            R.layout.activity_scrap_room
        ).also {
            it.initialPosition = intent.getIntExtra(KEY_INITIAL_POSITION, DEFAULT_POSITION)
            it.lifecycleOwner = this
            it.viewModel = scrapRoomViewModel
        }
    }

    private fun initScrapRoomViewModel() {
        scrapRoomViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(applicationContext).scrapRoomViewModelFactory(
                intent.getSerializableCompat(KEY_ROOMS) ?: return finish()
            )
        )[ScrapRoomViewModel::class.java]
    }

    companion object {

        private const val KEY_ROOMS = "rooms"
        private const val KEY_INITIAL_POSITION = "initial_position"
        private const val DEFAULT_POSITION = 0

        fun start(
            context: Context,
            rooms: RoomModels,
            position: Int
        ) {
            val intent = Intent(context, ScrapRoomActivity::class.java).apply {
                putExtra(KEY_ROOMS, rooms)
                putExtra(KEY_INITIAL_POSITION, position)
            }
            context.startActivity(intent)
        }
    }
}

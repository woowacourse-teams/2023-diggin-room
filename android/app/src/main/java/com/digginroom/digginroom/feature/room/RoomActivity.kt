package com.digginroom.digginroom.feature.room

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.data.di.ViewModelFactory
import com.digginroom.digginroom.databinding.ActivityRoomBinding
import com.digginroom.digginroom.feature.room.customview.RoomPagerAdapter
import com.digginroom.digginroom.feature.room.customview.roominfo.RoomInfoDialog
import com.digginroom.digginroom.feature.room.customview.roominfo.comment.dialog.CommentDialog
import com.digginroom.digginroom.feature.room.customview.roomplayer.RoomState
import com.digginroom.digginroom.feature.scrap.activity.ScrapListActivity
import com.digginroom.digginroom.model.RoomsModel

class RoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomBinding

    private val roomViewModel: RoomViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory.getInstance(applicationContext).roomViewModelFactory
        )[RoomViewModel::class.java]
    }

    private val roomInfoDialog: RoomInfoDialog = RoomInfoDialog()
    private val commentDialog: CommentDialog = CommentDialog()

    private val roomPagerAdapter: RoomPagerAdapter by lazy {
        RoomPagerAdapter(loadNextRoom = {
            roomViewModel.findNext()
        }, openComment = { id ->
            commentDialog.show(supportFragmentManager, id)
        }, openInfo = { track ->
            roomInfoDialog.show(supportFragmentManager, track)
        }, openScrap = {
            ScrapListActivity.start(this)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initRoomPager()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room)
        binding.lifecycleOwner = this
    }

    private fun initRoomPager() {
        binding.roomViewModel = roomViewModel
        binding.adapter = roomPagerAdapter
        roomViewModel.cachedRoom.observe(this) {
            when (it) {
                is RoomState.Error -> Unit
                RoomState.Loading -> Unit
                is RoomState.Success -> roomPagerAdapter.setData(it.rooms)
            }
        }
        repeat(3) {
            roomViewModel.findNext()
        }
    }

    override fun onResume() {
        super.onResume()
        roomPagerAdapter.play()
    }

    override fun onPause() {
        super.onPause()
        roomPagerAdapter.pause()
    }

    companion object {

        private const val KEY_ROOMS = "rooms"
        private const val KEY_INITIAL_POSITION = "initial_position"
        private const val DEFAULT_POSITION = 0
        fun start(context: Context) {
            val intent = Intent(context, RoomActivity::class.java)
            context.startActivity(intent)
        }

        fun start(
            context: Context,
            rooms: RoomsModel,
            position: Int
        ) {
            val intent = Intent(context, RoomActivity::class.java).apply {
                putExtra(KEY_ROOMS, rooms)
                putExtra(KEY_INITIAL_POSITION, position)
            }
            context.startActivity(intent)
        }
    }
}

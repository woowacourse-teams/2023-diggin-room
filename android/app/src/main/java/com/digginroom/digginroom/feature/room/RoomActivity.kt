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
import com.digginroom.digginroom.feature.room.customview.roominfoview.DefaultShowRoomInfoListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.TrackInfoDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.CommentDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.DefaultShowCommentsListener
import com.digginroom.digginroom.feature.room.customview.roomplayer.RoomState

class RoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoomBinding
    private val roomViewModel: RoomViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory.getInstance(applicationContext).roomViewModelFactory
        )[RoomViewModel::class.java]
    }
    private val commentViewModel: CommentViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory.getInstance(applicationContext).commentViewModelFactory
        )[CommentViewModel::class.java]
    }
    private var trackInfoDialog: TrackInfoDialog = TrackInfoDialog()
    private var commentDialog: CommentDialog = CommentDialog()

    private val roomPagerAdapter by lazy {
        RoomPagerAdapter(3) {
            roomViewModel.findNext()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initRoomPager()
        initListeners()
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

    private fun initListeners() {
        binding.showRoomInfoListener = DefaultShowRoomInfoListener(
            trackInfoDialog,
            supportFragmentManager
        )
        binding.showCommentsListener = DefaultShowCommentsListener(
            commentDialog,
            commentViewModel,
            supportFragmentManager,
            this
        )
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
        fun start(context: Context) {
            val intent = Intent(context, RoomActivity::class.java)
            context.startActivity(intent)
        }
    }
}

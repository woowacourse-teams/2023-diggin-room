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
import com.digginroom.digginroom.feature.room.customview.roominfoview.DefaultShowRoomInfoListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.TrackInfoDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.CommentViewModel
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.CommentDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.comment.dialog.listener.DefaultShowCommentsListener

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room)
        binding.lifecycleOwner = this
        binding.roomViewModel = roomViewModel.also { roomViewModel ->
            repeat(3) {
                roomViewModel.findNext()
            }
        }
        binding.showRoomInfoListener = DefaultShowRoomInfoListener(trackInfoDialog, this)
        binding.showCommentsListener =
            DefaultShowCommentsListener(commentDialog, commentViewModel, this)
    }

    override fun onResume() {
        super.onResume()
        binding.roomRoomPager.playCurrentRoom()
    }

    override fun onPause() {
        super.onPause()
        binding.roomRoomPager.pausePlayers()
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RoomActivity::class.java)
            context.startActivity(intent)
        }
    }
}

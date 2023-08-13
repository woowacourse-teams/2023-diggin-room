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
import com.digginroom.digginroom.feature.room.customview.roominfoview.CommentDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.CommentEventListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.ShowCommentsListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.ShowRoomInfoListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.TrackInfoDialog
import com.digginroom.digginroom.model.TrackModel

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
    private var dialog: TrackInfoDialog = TrackInfoDialog()
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
        binding.showRoomInfoListener = object : ShowRoomInfoListener {
            override fun show(trackModel: TrackModel) {
                if (dialog.isAdded) return
                dialog.show(supportFragmentManager, "")
                dialog.updateTrackModel(trackModel)
                dialog.isCancelable = true
            }
        }
        binding.showCommentsListener = object : ShowCommentsListener {
            override fun show(roomId: Long) {
                if (commentDialog.isAdded) return
                commentDialog.show(supportFragmentManager, "")
                commentDialog.setViewModel(commentViewModel)
                commentDialog.setCommentEventListener(object : CommentEventListener {
                    override fun findComments() {
                        commentViewModel.findComments(roomId)
                    }

                    override fun sendComment(comment: String) {
                        commentViewModel.postComment(roomId, comment)
                    }

                    override fun updateComment() {
                        TODO("Not yet implemented")
                    }

                    override fun deleteComment() {
                        TODO("Not yet implemented")
                    }
                })
            }
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RoomActivity::class.java)
            context.startActivity(intent)
        }
    }
}

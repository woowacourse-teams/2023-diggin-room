package com.digginroom.digginroom.feature.room

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.data.di.ViewModelFactory
import com.digginroom.digginroom.databinding.ActivityRoomBinding
import com.digginroom.digginroom.feature.room.customview.CommentState
import com.digginroom.digginroom.feature.room.customview.roominfoview.CommentDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.CommentEventListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.CommentMenuDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.CommentMenuEventListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.DeleteCommentResultListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.PostCommentResultListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.ShowCommentsListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.ShowRoomInfoListener
import com.digginroom.digginroom.feature.room.customview.roominfoview.TrackInfoDialog
import com.digginroom.digginroom.feature.room.customview.roominfoview.UpdateCommentResultListener
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
    private var commentMenuDialog: CommentMenuDialog = CommentMenuDialog()

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

                    override fun postComment(comment: String) {
                        commentViewModel.postComment(roomId, comment)
                    }

                    override fun updateComment(
                        commentId: Long,
                        comment: String,
                        updatedPosition: Int
                    ) {
                        commentViewModel.updateComment(roomId, commentId, comment, updatedPosition)
                    }

                    override fun deleteComment(
                        commentId: Long,
                        updatedPosition: Int
                    ) {
                        commentViewModel.deleteComment(roomId, commentId, updatedPosition)
                    }
                })
                commentDialog.setPostCommentResultListener(
                    PostCommentResultListener(this@RoomActivity)
                )
                commentDialog.setUpdateCommentResultListener(
                    UpdateCommentResultListener(this@RoomActivity)
                )
                commentDialog.setDeleteCommentResultListener(
                    DeleteCommentResultListener(this@RoomActivity)
                )
                commentDialog.setShowCommentMenuListener { selectedComment, selectedPosition ->
                    if (commentMenuDialog.isAdded) return@setShowCommentMenuListener
                    commentMenuDialog.show(supportFragmentManager, "")
                    commentMenuDialog.setEventListener(object : CommentMenuEventListener {
                        override fun update() {
                            commentViewModel.updateCommentState(
                                CommentState.Edit.Ready
                            )
                            commentViewModel.setComment(selectedComment.comment)
                            commentDialog.setSelectedCommentId(selectedComment.id)
                            commentDialog.setSelectedPosition(selectedPosition)
                            commentMenuDialog.dismiss()
                        }

                        override fun delete() {
                            val builder = AlertDialog.Builder(this@RoomActivity)
                            builder.setMessage("정말 삭제하사겠습니까?")
                                .setPositiveButton("삭제") { dialog, id ->
                                    commentViewModel.deleteComment(
                                        roomId,
                                        selectedComment.id,
                                        selectedPosition
                                    )
                                    if (commentMenuDialog.isAdded) commentMenuDialog.dismiss()
                                }
                                .setNegativeButton("취소") { dialog, id ->
                                    if (commentMenuDialog.isAdded) commentMenuDialog.dismiss()
                                }
                            // Create the AlertDialog object and return it
                            builder.create()
                            builder.show()
                        }
                    })
                    commentMenuDialog.updateComment(selectedComment)
                }
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

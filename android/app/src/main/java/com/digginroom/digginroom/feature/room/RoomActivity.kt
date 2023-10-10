package com.digginroom.digginroom.feature.room

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.ActivityRoomBinding
import com.digginroom.digginroom.feature.room.comment.dialog.CommentDialog
import com.digginroom.digginroom.feature.room.customview.roomplayer.RoomState
import com.digginroom.digginroom.feature.room.roominfo.RoomInfoDialog
import com.digginroom.digginroom.feature.scrap.activity.ScrapListActivity
import com.digginroom.digginroom.feature.tutorial.TutorialFragment
import com.digginroom.digginroom.model.RoomsModel
import com.digginroom.digginroom.util.getSerializable
import com.dygames.androiddi.ViewModelDependencyInjector.injectViewModel
import com.dygames.roompager.PagingOrientation

class RoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomBinding

    private val roomViewModel: RoomViewModel by lazy {
        ViewModelProvider(
            this,
            injectViewModel<RoomViewModel>()
        )[RoomViewModel::class.java]
    }

    private val roomInfoDialog: RoomInfoDialog = RoomInfoDialog()
    private val commentDialog: CommentDialog = CommentDialog()

    private val roomPagerAdapter: RoomPagerAdapter by lazy {
        RoomPagerAdapter(loadNextRoom = {
            roomViewModel.findNext()
        }, dislikeRoom = { id ->
                roomViewModel.postDislike(id)
            }, openComment = { id ->
                commentDialog.show(supportFragmentManager, id)
            }, openInfo = { track ->
                roomInfoDialog.show(supportFragmentManager, track)
            }, openScrap = {
                ScrapListActivity.start(this)
            }, scrap = { id ->
                roomViewModel.postScrap(id)
            }, unScrap = { id ->
                roomViewModel.removeScrap(id)
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initRoomPager()
        initTutorial()
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
        roomPagerAdapter.setData(
            intent.getSerializable<RoomsModel>(KEY_ROOMS)?.value ?: emptyList()
        )
        binding.roomRoomPager.setRoomLoadable(roomPagerAdapter.rooms.isEmpty())
        binding.roomRoomPager.setOrientation(
            PagingOrientation.values()[
                intent.getIntExtra(
                    KEY_PAGING_ORIENTATION,
                    2
                )
            ]
        )
        binding.roomRoomPager.setRoomPosition(intent.getIntExtra(KEY_INITIAL_POSITION, 0))
        if (binding.roomRoomPager.isNextRoomLoadable) {
            repeat(3) {
                roomViewModel.findNext()
            }
        }
    }

    private fun initTutorial() {
        val tutorialCompleted = intent.getBooleanExtra(KEY_TUTORIAL_COMPLETED, true)
        navigateToTutorial(tutorialCompleted)
    }

    override fun onResume() {
        super.onResume()
        roomPagerAdapter.play()
    }

    private fun navigateToTutorial(tutorialCompleted: Boolean) {
        if (tutorialCompleted) return
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.room_fragment_container, TutorialFragment())
        }
    }

    override fun onPause() {
        super.onPause()
        roomPagerAdapter.pause()
    }

    companion object {

        private const val KEY_ROOMS = "rooms"
        private const val KEY_INITIAL_POSITION = "initial_position"
        private const val KEY_PAGING_ORIENTATION = "paging_orientation"
        private const val KEY_TUTORIAL_COMPLETED = "tutorial_completed"
        fun start(context: Context) {
            val intent = Intent(context, RoomActivity::class.java)
            context.startActivity(intent)
        }

        fun start(
            context: Context,
            rooms: RoomsModel,
            position: Int,
            pagingOrientation: PagingOrientation
        ) {
            val intent = Intent(context, RoomActivity::class.java).apply {
                putExtra(KEY_ROOMS, rooms)
                putExtra(KEY_INITIAL_POSITION, position)
                putExtra(KEY_PAGING_ORIENTATION, pagingOrientation.ordinal)
            }
            context.startActivity(intent)
        }

        fun start(
            context: Context,
            tutorialCompleted: Boolean
        ) {
            val intent = Intent(context, RoomActivity::class.java).apply {
                putExtra(KEY_TUTORIAL_COMPLETED, tutorialCompleted)
            }
            context.startActivity(intent)
        }
    }
}

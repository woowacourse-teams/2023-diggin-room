package com.digginroom.digginroom.feature.room

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.ActivityRoomBinding
import com.digginroom.digginroom.feature.room.comment.dialog.CommentDialog
import com.digginroom.digginroom.feature.room.customview.roomplayer.RoomState
import com.digginroom.digginroom.feature.room.roominfo.RoomInfoDialog
import com.digginroom.digginroom.feature.room.roominfo.RoomInfoEvent
import com.digginroom.digginroom.feature.room.roominfo.RoomInfoType
import com.digginroom.digginroom.feature.scrap.activity.ScrapListActivity
import com.digginroom.digginroom.feature.setting.SettingActivity
import com.digginroom.digginroom.feature.tutorial.TutorialFragment
import com.digginroom.digginroom.model.RoomsModel
import com.digginroom.digginroom.model.TrackModel
import com.digginroom.digginroom.model.mapper.RoomMapper.toDomain
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
        RoomPagerAdapter(
            loadNextRoom = {
                roomViewModel.findNext()
            },
            dislikeRoom = { id ->
                roomViewModel.postDislike(id)
            },
            roomInfoEvent = RoomInfoEvent(
                openSetting = {
                    SettingActivity.start(this)
                },
                openComment = { id ->
                    commentDialog.show(supportFragmentManager, id)
                },
                openInfo = { track ->
                    roomInfoDialog.show(supportFragmentManager, track)
                },
                openScrap = {
                    ScrapListActivity.start(this)
                },
                scrap = { id ->
                    roomViewModel.postScrap(id)
                },
                unScrap = { id ->
                    roomViewModel.removeScrap(id)
                },
                copyInfo = ::copyRoomInfo,
                cancel = ::finish
            ),
            roomInfoType = intent.getSerializable(KEY_ROOM_INFO_TYPE) ?: RoomInfoType.GENERAL
        )
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
                is RoomState.Success -> {
                    roomPagerAdapter.setData(it.rooms)
                }
            }
        }
        val rooms = intent.getSerializable<RoomsModel>(KEY_ROOMS)?.value ?: emptyList()
        roomPagerAdapter.setData(
            rooms
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
        } else {
            roomViewModel.setRooms(rooms.map { it.toDomain() })
        }
    }

    private fun initTutorial() {
        if (intent.getBooleanExtra(KEY_TUTORIAL_COMPLETED, true)) return
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.room_fragment_container, TutorialFragment())
        }
    }

    override fun onResume() {
        super.onResume()
        roomPagerAdapter.play()
        roomViewModel.findScrappedRooms()
    }

    override fun onPause() {
        super.onPause()
        roomPagerAdapter.pause()
    }

    private fun copyRoomInfo(trackModel: TrackModel) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(
            ClipData.newPlainText(
                "",
                "${trackModel.artist} - ${trackModel.title}"
            )
        )
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
            Toast.makeText(
                this,
                getString(R.string.room_clipboard),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {

        private const val KEY_ROOMS = "rooms"
        private const val KEY_INITIAL_POSITION = "initial_position"
        private const val KEY_PAGING_ORIENTATION = "paging_orientation"
        private const val KEY_TUTORIAL_COMPLETED = "tutorial_completed"
        private const val KEY_ROOM_INFO_TYPE = "room_info_type"

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
                putExtra(KEY_ROOM_INFO_TYPE, RoomInfoType.SCRAPPED)
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

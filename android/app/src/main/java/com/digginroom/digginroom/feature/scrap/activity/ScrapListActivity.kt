package com.digginroom.digginroom.feature.scrap.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.ActivityScrapBinding
import com.digginroom.digginroom.feature.login.SocialLogin
import com.digginroom.digginroom.feature.room.RoomActivity
import com.digginroom.digginroom.feature.scrap.adapter.ScrapAdapter
import com.digginroom.digginroom.feature.scrap.adapter.ScrapRoomClickListener
import com.digginroom.digginroom.feature.scrap.viewmodel.ScrapUiEvent
import com.digginroom.digginroom.feature.scrap.viewmodel.ScrapUiState
import com.digginroom.digginroom.feature.scrap.viewmodel.ScrapViewModel
import com.digginroom.digginroom.model.RoomModel
import com.digginroom.digginroom.model.RoomsModel
import com.dygames.androiddi.ViewModelDependencyInjector.injectViewModel
import com.dygames.roompager.PagingOrientation

class ScrapListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrapBinding
    private val scrapViewModel: ScrapViewModel by lazy {
        ViewModelProvider(
            this,
            injectViewModel<ScrapViewModel>()
        )[ScrapViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()

        scrapViewModel.findScrappedRooms()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initScrapBinding()
        initScrapStateObserver()
        initScrapEventObserver()
    }

    private fun initScrapBinding() {
        binding =
            DataBindingUtil.setContentView<ActivityScrapBinding>(this, R.layout.activity_scrap)
                .also {
                    it.lifecycleOwner = this
                    it.viewModel = scrapViewModel
                    it.adapter = ScrapAdapter().apply {
                        itemClickListener = ScrapRoomClickListener { position ->
                            scrapViewModel.uiState
                                .value
                                ?.onSelect
                                ?.invoke(position)
                        }
                    }
                    it.scrapRvScrappedRooms.apply {
                        setHasFixedSize(true)
                        itemAnimator = null
                    }
                    it.scrapIvBack.setOnClickListener { finish() }
                }
    }

    private fun initScrapStateObserver() {
        val googleLoginResultLauncher = getGoogleAuthCodeResultLauncher()

        scrapViewModel.uiState.observe(this) {
            when (it) {
                is ScrapUiState.Navigation -> navigateToRoomView(
                    it.position,
                    it.rooms.map { scrapped -> scrapped.room }
                )

                is ScrapUiState.PlaylistExtraction -> googleLoginResultLauncher.launch(
                    SocialLogin.Google.getIntent(this)
                )

                else -> binding.adapter?.submitList(
                    scrapViewModel.uiState.value?.rooms
                )
            }
        }
    }

    private fun initScrapEventObserver() {
        scrapViewModel.event.observe(this) {
            when (it) {
                is ScrapUiEvent.LoadingExtraction -> Toast.makeText(
                    this,
                    getString(R.string.scrap_extracting_playlist).format(it.expectedTime),
                    Toast.LENGTH_SHORT
                ).show()

                is ScrapUiEvent.DisAllowedExtraction -> Toast.makeText(
                    this,
                    getString(R.string.scrap_disallowed_extracting_playlist),
                    Toast.LENGTH_SHORT
                ).show()

                else -> {}
            }
        }
    }

    private fun getGoogleAuthCodeResultLauncher(): ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                SocialLogin.Google
                    .getAuthCode(result)
                    ?.let { authCode ->
                        scrapViewModel.extractPlaylist(authCode)
                    }
            }
        }

    private fun navigateToRoomView(index: Int, rooms: List<RoomModel>) {
        RoomActivity.start(
            context = this,
            rooms = RoomsModel(rooms),
            position = index,
            pagingOrientation = PagingOrientation.VERTICAL
        )
    }

    companion object {

        private const val RESULT_OK = -1

        fun start(context: Context) {
            val intent = Intent(context, ScrapListActivity::class.java)
            context.startActivity(intent)
        }
    }
}

package com.digginroom.digginroom.feature.scrap.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.ActivityScrapBinding
import com.digginroom.digginroom.feature.room.RoomActivity
import com.digginroom.digginroom.feature.scrap.adapter.ScrapAdapter
import com.digginroom.digginroom.feature.scrap.adapter.ScrapRoomClickListener
import com.digginroom.digginroom.feature.scrap.viewmodel.ScrapUiState
import com.digginroom.digginroom.feature.scrap.viewmodel.ScrapViewModel
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
        initScrapObserver()
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
                                .getValue()
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

    private fun initScrapObserver() {
        scrapViewModel.uiState.observe(this) {
            when (it) {
                is ScrapUiState.Navigation -> navigateToRoomView(it.targetIndex)

                else -> binding.adapter?.submitList(it.rooms)
            }
        }
    }

    private fun navigateToRoomView(index: Int) {
        RoomActivity.start(
            context = this,
            rooms = RoomsModel(scrapViewModel.uiState.getValue()!!.rooms.map { it.room }),
            position = index,
            pagingOrientation = PagingOrientation.VERTICAL
        )
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, ScrapListActivity::class.java)
            context.startActivity(intent)
        }
    }
}

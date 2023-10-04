package com.digginroom.digginroom.feature.scrap.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.ActivityScrapBinding
import com.digginroom.digginroom.feature.room.RoomActivity
import com.digginroom.digginroom.feature.scrap.adapter.ScrapAdapter
import com.digginroom.digginroom.feature.scrap.navigator.DefaultScrapNavigator
import com.digginroom.digginroom.feature.scrap.viewmodel.ScrapViewModel
import com.digginroom.digginroom.model.RoomsModel
import com.dygames.androiddi.ViewModelDependencyInjector.injectViewModel

class ScrapListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrapBinding
    private val scrapViewModel: ScrapViewModel by lazy {
        ViewModelProvider(
            this,
            injectViewModel<ScrapViewModel>()
        )[ScrapViewModel::class.java]
    }
    private lateinit var scrappedRooms: RoomsModel

    override fun onResume() {
        super.onResume()

        scrapViewModel.findScrappedRooms()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initScrapBinding()
        initScrappedRoomsObserver()
        initBackPressedCallback()
    }

    private fun initScrapBinding() {
        binding =
            DataBindingUtil.setContentView<ActivityScrapBinding>(this, R.layout.activity_scrap)
                .also {
                    it.lifecycleOwner = this
                    it.viewModel = scrapViewModel
                    it.adapter = ScrapAdapter()
                    it.navigateToScrapRoomView = DefaultScrapNavigator(this)::navigate
                    it.scrapIvBack.setOnClickListener { finish() }
                }
    }

    private fun initScrappedRoomsObserver() {
        scrapViewModel.scrappedRooms.observe(this) {
            scrappedRooms = RoomsModel(it)
        }
    }

    private fun initBackPressedCallback() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    setResult(
                        RESULT_OK,
                        RoomActivity.getIntentForResultWithScrappedRooms(this@ScrapListActivity, scrappedRooms)
                    )
                    if (!isFinishing) finish()
                }
            }
        )
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ScrapListActivity::class.java)
            context.startActivity(intent)
        }
    }
}

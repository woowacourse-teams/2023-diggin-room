package com.digginroom.digginroom.feature.scrap.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.data.di.ViewModelFactory
import com.digginroom.digginroom.databinding.ActivityScrapBinding
import com.digginroom.digginroom.feature.scrap.adapter.ScrapAdapter
import com.digginroom.digginroom.feature.scrap.navigator.DefaultScrapNavigator
import com.digginroom.digginroom.feature.scrap.viewmodel.ScrapViewModel

class ScrapListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrapBinding
    private val scrapViewModel: ScrapViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory.getInstance(applicationContext).scrapViewModelFactory
        )[ScrapViewModel::class.java]
    }
    override fun onResume() {
        super.onResume()

        scrapViewModel.findScrappedRooms()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initScrapBinding()
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

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ScrapListActivity::class.java)
            context.startActivity(intent)
        }
    }
}

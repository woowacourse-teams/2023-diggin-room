package com.digginroom.digginroom.views.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.data.di.ViewModelFactory
import com.digginroom.digginroom.databinding.ActivityScrappedRoomBinding
import com.digginroom.digginroom.viewmodels.ScrapViewModel

class ScrappedRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScrappedRoomBinding
    private val viewModel: ScrapViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory.getInstance(applicationContext).scrapViewModelFactory
        )[ScrapViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityScrappedRoomBinding>(
            this,
            R.layout.activity_scrapped_room
        ).also {
            it.viewModel = viewModel
        }
    }
}

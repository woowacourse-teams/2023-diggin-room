package com.digginroom.digginroom.feature.genretaste

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.databinding.ActivityGenreTasteBinding
import com.digginroom.digginroom.feature.genretaste.adpater.GenreTasteAdapter
import com.digginroom.digginroom.feature.room.RoomActivity
import com.dygames.androiddi.ViewModelDependencyInjector.injectViewModel

class GenreTasteActivity : AppCompatActivity() {

    lateinit var binding: ActivityGenreTasteBinding
    private val genreTasteViewModel: GenreTasteViewModel by lazy {
        ViewModelProvider(
            this,
            injectViewModel<GenreTasteViewModel>()
        )[GenreTasteViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initGenreTasteBinding()
        initGenreTasteObserver()
    }

    private fun initGenreTasteBinding() {
        binding = DataBindingUtil.setContentView<ActivityGenreTasteBinding>(
            this,
            R.layout.activity_genre_taste
        ).also {
            it.lifecycleOwner = this
            it.viewModel = genreTasteViewModel
            it.adapter = GenreTasteAdapter()
        }
    }

    private fun initGenreTasteObserver() {
        genreTasteViewModel.uiState.observe(this) {
            when (it) {
                is GenreTasteUiState.InProgress -> binding.adapter?.update(it.genreTasteSelection)

                is GenreTasteUiState.Succeed -> {
                    finish()
                    RoomActivity.start(this, false)
                }

                is GenreTasteUiState.Failed -> {}
            }
        }
    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, GenreTasteActivity::class.java)

            context.startActivity(intent)
        }
    }
}

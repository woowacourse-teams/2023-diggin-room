package com.digginroom.digginroom.feature.genretaste

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.digginroom.digginroom.R
import com.digginroom.digginroom.data.di.ViewModelFactory
import com.digginroom.digginroom.databinding.ActivityGenreTasteBinding

class GenreTasteActivity : AppCompatActivity() {

    lateinit var binding: ActivityGenreTasteBinding
    private val genreTasteViewModel: GenreTasteViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory.getInstance(applicationContext).genreTasteViewModelFactory
        )[GenreTasteViewModel::class.java]
    }
    private val adapter by lazy {
        GenreTasteAdapter { genreTasteModel ->
            genreTasteViewModel.switchSelection(genreTasteModel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initGenreTasteBinding()
    }

    private fun initGenreTasteBinding() {
        binding = DataBindingUtil.setContentView<ActivityGenreTasteBinding>(
            this,
            R.layout.activity_genre_taste
        ).also {
            it.viewModel = genreTasteViewModel
            it.adapter = adapter
        }

        genreTasteViewModel.genres.observe(this) {
            adapter.submitList(it.toMutableList())
        }
    }
}

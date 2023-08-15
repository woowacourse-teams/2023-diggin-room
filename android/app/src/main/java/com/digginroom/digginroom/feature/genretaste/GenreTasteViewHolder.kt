package com.digginroom.digginroom.feature.genretaste

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.databinding.ItemGenreBinding
import com.digginroom.digginroom.model.GenreTasteModel

class GenreTasteViewHolder(private val binding: ItemGenreBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(genreTasteModel: GenreTasteModel) {
        binding.model = genreTasteModel
    }

    companion object {

        fun of(parent: ViewGroup, clickListener: GenreTasteClickListener): GenreTasteViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemGenreBinding.inflate(layoutInflater, parent, false).apply {
                this.clickListener = clickListener
            }

            return GenreTasteViewHolder(binding)
        }
    }
}

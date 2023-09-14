package com.digginroom.digginroom.feature.genretaste.adpater

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.digginroom.digginroom.feature.genretaste.GenreTasteClickListener
import com.digginroom.digginroom.feature.genretaste.GenreTasteViewHolder
import com.digginroom.digginroom.model.GenreTasteModel
import com.digginroom.digginroom.model.GenreTasteSelectionModel

class GenreTasteAdapter :
    ListAdapter<GenreTasteModel, GenreTasteViewHolder>(GenreTasteDiffUtilCallback()) {

    private var onGenreClick: GenreTasteClickListener = GenreTasteClickListener {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreTasteViewHolder {
        return GenreTasteViewHolder.of(
            parent = parent,
            clickListener = onGenreClick
        )
    }

    override fun onBindViewHolder(holder: GenreTasteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: MutableList<GenreTasteModel>?) {
        super.submitList(list)
    }

    fun update(genreTasteSelectionModel: GenreTasteSelectionModel) {
        submitList(genreTasteSelectionModel.genresTaste.toMutableList())
        onGenreClick = GenreTasteClickListener { genreTasteSelectionModel.onSelect(it) }
    }
}

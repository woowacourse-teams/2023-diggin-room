package com.digginroom.digginroom.feature.genretaste

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.digginroom.digginroom.model.GenreTasteModel

class GenreTasteAdapter(
    private val genreTasteClickListener: GenreTasteClickListener
) : ListAdapter<GenreTasteModel, GenreTasteViewHolder>(GenreTasteDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreTasteViewHolder {
        return GenreTasteViewHolder.of(
            parent = parent,
            clickListener = genreTasteClickListener
        )
    }

    override fun onBindViewHolder(holder: GenreTasteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: MutableList<GenreTasteModel>?) {
        super.submitList(list)
    }
}

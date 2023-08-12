package com.digginroom.digginroom.feature.genretaste

import androidx.recyclerview.widget.DiffUtil
import com.digginroom.digginroom.model.GenreTasteModel

class GenreTasteDiffUtilCallback : DiffUtil.ItemCallback<GenreTasteModel>() {

    override fun areItemsTheSame(oldItem: GenreTasteModel, newItem: GenreTasteModel): Boolean =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: GenreTasteModel, newItem: GenreTasteModel): Boolean =
        oldItem.isSelected == newItem.isSelected
}

package com.digginroom.digginroom.feature.scrap.adapter

import androidx.recyclerview.widget.DiffUtil
import com.digginroom.digginroom.model.ScrappedRoomModel

class ScrapRoomDiffUtilCallback : DiffUtil.ItemCallback<ScrappedRoomModel>() {

    override fun areItemsTheSame(oldItem: ScrappedRoomModel, newItem: ScrappedRoomModel): Boolean {
        return oldItem.room == newItem.room && oldItem.isSelected == newItem.isSelected
    }

    override fun areContentsTheSame(oldItem: ScrappedRoomModel, newItem: ScrappedRoomModel): Boolean {
        return oldItem.room == newItem.room && oldItem.isSelected == newItem.isSelected
    }
}

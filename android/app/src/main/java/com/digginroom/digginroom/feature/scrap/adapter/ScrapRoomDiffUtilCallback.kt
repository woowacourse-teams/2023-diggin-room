package com.digginroom.digginroom.feature.scrap.adapter

import androidx.recyclerview.widget.DiffUtil
import com.digginroom.digginroom.model.RoomModel

class ScrapRoomDiffUtilCallback : DiffUtil.ItemCallback<RoomModel>() {

    override fun areItemsTheSame(oldItem: RoomModel, newItem: RoomModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RoomModel, newItem: RoomModel): Boolean {
        return oldItem == newItem
    }
}

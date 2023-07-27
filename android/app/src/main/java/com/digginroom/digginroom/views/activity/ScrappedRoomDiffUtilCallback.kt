package com.digginroom.digginroom.views.activity

import androidx.recyclerview.widget.DiffUtil
import com.digginroom.digginroom.views.model.RoomModel

class ScrappedRoomDiffUtilCallback : DiffUtil.ItemCallback<RoomModel>() {

    override fun areItemsTheSame(oldItem: RoomModel, newItem: RoomModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RoomModel, newItem: RoomModel): Boolean {
        return oldItem == newItem
    }
}

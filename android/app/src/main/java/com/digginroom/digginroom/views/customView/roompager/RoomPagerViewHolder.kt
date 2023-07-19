package com.digginroom.digginroom.views.customView.roompager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.databinding.ItemRoomPagerBinding
import com.digginroom.digginroom.views.model.RoomModel

class RoomPagerViewHolder(private val binding: ItemRoomPagerBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(roomModel: RoomModel) {
        if (binding.room == roomModel) {
            return
        }
        binding.room = roomModel
    }

    companion object {
        fun create(parent: ViewGroup): RoomPagerViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRoomPagerBinding.inflate(layoutInflater, parent, false)
            return RoomPagerViewHolder(binding)
        }
    }
}

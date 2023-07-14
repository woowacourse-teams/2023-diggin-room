package com.digginroom.digginroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.databinding.ItemRoomBinding
import com.digginroom.model.TempUiModel

class RoomViewHolder(private val binding: ItemRoomBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(tempUIModel: TempUiModel) {
        binding.ivRoom.setImageResource(tempUIModel.videoId)
    }

    companion object {
        fun create(parent: ViewGroup): RoomViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRoomBinding.inflate(layoutInflater, parent, false)
            return RoomViewHolder(binding)
        }
    }
}

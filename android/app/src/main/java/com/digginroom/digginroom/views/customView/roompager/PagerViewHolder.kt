package com.digginroom.digginroom.views.customView.roompager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.databinding.ItemRecyclerViewBinding
import com.digginroom.digginroom.views.model.RoomModel

class PagerViewHolder(private val binding: ItemRecyclerViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(roomModel: RoomModel) {
        binding.ivRoom.setImageResource(roomModel.videoId)
    }

    companion object {
        fun create(parent: ViewGroup): PagerViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRecyclerViewBinding.inflate(layoutInflater, parent, false)
            return PagerViewHolder(binding)
        }
    }
}

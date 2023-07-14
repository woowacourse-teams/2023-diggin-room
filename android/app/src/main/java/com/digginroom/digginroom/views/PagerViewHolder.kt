package com.digginroom.digginroom.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.databinding.ItemRoomBinding
import com.digginroom.model.TempUiModel

class PagerViewHolder(private val binding: ItemRoomBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(tempUIModel: TempUiModel) {
        binding.ivRoom.setImageResource(tempUIModel.videoId)
    }

    companion object {
        fun create(parent: ViewGroup): PagerViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRoomBinding.inflate(layoutInflater, parent, false)
            return PagerViewHolder(binding)
        }
    }
}

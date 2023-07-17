package com.digginroom.digginroom.views.roompager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.databinding.ItemRecyclerViewBinding
import com.digginroom.model.TempUiModel

class PagerViewHolder(private val binding: ItemRecyclerViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(tempUIModel: TempUiModel) {
        binding.ivRoom.setImageResource(tempUIModel.videoId)
    }

    companion object {
        fun create(parent: ViewGroup): PagerViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRecyclerViewBinding.inflate(layoutInflater, parent, false)
            return PagerViewHolder(binding)
        }
    }
}

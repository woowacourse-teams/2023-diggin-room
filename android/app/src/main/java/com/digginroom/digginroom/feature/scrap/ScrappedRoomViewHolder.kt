package com.digginroom.digginroom.feature.scrap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.databinding.ItemScrappedRoomBinding
import com.digginroom.digginroom.model.RoomModel

data class ScrappedRoomViewHolder(
    private val binding: ItemScrappedRoomBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(scrappedRoom: RoomModel) {
        binding.scrappedRoom = scrappedRoom
    }

    companion object {

        fun from(parent: ViewGroup): ScrappedRoomViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemScrappedRoomBinding.inflate(layoutInflater, parent, false)

            return ScrappedRoomViewHolder(binding)
        }
    }
}

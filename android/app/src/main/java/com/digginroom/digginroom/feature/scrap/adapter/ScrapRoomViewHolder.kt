package com.digginroom.digginroom.feature.scrap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.databinding.ItemScrappedRoomBinding
import com.digginroom.digginroom.model.ScrappedRoomModel

data class ScrapRoomViewHolder(
    private val binding: ItemScrappedRoomBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(scrappedRoom: ScrappedRoomModel) {
        with(binding) {
            position = adapterPosition
            room = scrappedRoom
        }
    }

    companion object {

        fun of(
            parent: ViewGroup,
            clickListener: ScrapRoomClickListener
        ): ScrapRoomViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemScrappedRoomBinding.inflate(layoutInflater, parent, false).apply {
                listener = clickListener
            }

            return ScrapRoomViewHolder(binding)
        }
    }
}

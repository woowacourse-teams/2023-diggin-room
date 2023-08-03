package com.digginroom.digginroom.feature.scrap.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.digginroom.digginroom.model.RoomModel

class ScrapAdapter : ListAdapter<RoomModel, ScrapRoomViewHolder>(ScrapRoomDiffUtilCallback()) {
    var itemClickListener: ScrapRoomClickListener = ScrapRoomClickListener { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrapRoomViewHolder {
        return ScrapRoomViewHolder.of(parent) { position ->
            itemClickListener.onClick(position)
        }
    }

    override fun onBindViewHolder(holder: ScrapRoomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: MutableList<RoomModel>?) {
        super.submitList(list)
    }
}

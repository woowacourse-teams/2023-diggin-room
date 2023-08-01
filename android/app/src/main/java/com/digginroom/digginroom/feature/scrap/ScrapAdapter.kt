package com.digginroom.digginroom.feature.scrap

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.digginroom.digginroom.model.RoomModel

class ScrapAdapter :
    ListAdapter<RoomModel, ScrappedRoomViewHolder>(ScrappedRoomDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrappedRoomViewHolder {
        return ScrappedRoomViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ScrappedRoomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: MutableList<RoomModel>?) {
        super.submitList(list)
    }
}

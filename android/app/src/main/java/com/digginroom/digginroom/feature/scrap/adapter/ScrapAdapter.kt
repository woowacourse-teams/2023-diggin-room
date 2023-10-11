package com.digginroom.digginroom.feature.scrap.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.digginroom.digginroom.model.ScrappedRoomModel

class ScrapAdapter :
    ListAdapter<ScrappedRoomModel, ScrapRoomViewHolder>(ScrapRoomDiffUtilCallback()) {

    var itemClickListener: ScrapRoomClickListener = ScrapRoomClickListener { }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).room.roomId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrapRoomViewHolder {
        return ScrapRoomViewHolder.of(parent) { position ->
            itemClickListener.onClick(position)
        }
    }

    override fun onBindViewHolder(holder: ScrapRoomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<ScrappedRoomModel>?) {
        super.submitList(list)
    }
}

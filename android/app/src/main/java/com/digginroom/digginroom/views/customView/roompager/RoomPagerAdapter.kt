package com.digginroom.digginroom.views.customView.roompager

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digginroom.digginroom.views.model.RoomModel

class RoomPagerAdapter(private var data: List<RoomModel>) : RecyclerView.Adapter<RoomPagerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomPagerViewHolder {
        return RoomPagerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RoomPagerViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setData(updateData: List<RoomModel>) {
        data = updateData
        notifyDataSetChanged()
    }
}
